package com.newsclan.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class VTM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filepath = "demo.srt";
		readSrtFile(filepath);
	}

	private static void readSrtFile(String filepath) {
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			Scanner input = new Scanner(fis);
			while (input.hasNextLine()) {
				sb.append(input.nextLine());
				sb.append("\n");

			}
			List<String[]> list = parseSRT(sb.toString());
			for (String[] strings : list) {
				for (String string : strings) {
					System.out.print(string + " ");
				}
				System.out.println();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String srt2html(String srt) {
		StringBuilder sb = new StringBuilder();
		List<String[]> srtList = parseSRT(srt);
		for (String[] strings : srtList) {
			String start_time = strings[1];
			String end_time = strings[2];
			String tagText = strings[3];
			String X = strings[4];
			String Y = strings[5];
			String W = strings[6];
			String H = strings[7];

			sb.append("<tr start_time='" + start_time + "' end_time='" + end_time + "' tagText='" + tagText + "' x='"
					+ X + "' y='" + Y + "' w='" + W + "' h='" + H + "'>");
			sb.append("<td><button class='btn' onclick='jump($(this).text())' >");
			sb.append(start_time);
			sb.append("</button></td>");
			sb.append("<td><button class='btn' onclick='jump($(this).text())' >");
			sb.append(end_time);
			sb.append(
					"</button></td><td onDblClick='fastEdit(this)' class='text-center' style='vertical-align:middle'>");
			sb.append(tagText);
			sb.append("</td>");
			sb.append(
					"<td  class='text-center' style='vertical-align:middle'><span class='glyphicon glyphicon-trash' onclick='$(this).parent().parent().remove()'></span></td>");
			sb.append("</tr>");

		}
		return sb.toString();
	}

	public static List<String[]> parseSRT(String srt) {
		List<String[]> ret = new LinkedList<String[]>();
		StringReader sis = new StringReader(srt);
		Scanner input = new Scanner(sis);

		String num = null;
		String time, times[];
		String tagText;
		String area, areas[], XY[], WH[];
		while (input.hasNextLine()) {
			try {
				num = input.nextLine();
				time = input.nextLine();
				times = time.split(" --> ");
				tagText = input.nextLine();
				area = input.nextLine();
				areas = area.split("-");
				areas[0] = areas[0].substring(1, areas[0].length() - 1);
				areas[1] = areas[1].substring(1, areas[1].length() - 1);
				XY = areas[0].split(",");
				WH = areas[1].split(",");

				if (input.hasNextLine())
					input.nextLine();
				ret.add(new String[] { num, times[0], times[1], tagText, XY[0], XY[1], WH[0], WH[1] });
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(num);
			}
		}

		sis.close();

		return ret;
	}

	public static String build(final Long build_task_id) {
		System.out.println("building task:" + build_task_id);
		String manual_task_id = DAO.queryString("select tb_manual_task_id from tb_build_task where id=?",
				build_task_id);
		List task = DAO.queryList("select tb_movie_id,fastsave from tb_manual_task where id=?", false, manual_task_id)
				.get(0);
		final String movie_path = DAO.queryString("select movie_file from tb_movie where id=?", task.get(0));
		String srt = (String) task.get(1);
		final List<String[]> subtasks = parseSRT(srt);
		if (subtasks.size() > 0)
			DAO.update("update tb_build_task set sub_task_num=? where id=?", subtasks.size(), build_task_id);
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int i=0;
					for (String[] subtask : subtasks) {

						String[] envp = new String[] {};
						String[] cmdarray = new String[3];
						System.out.println("movie_path:"+movie_path);
						String extense = movie_path.substring(movie_path.length() - 4);
						String output = Config.path + "output/" + build_task_id;
						System.out.println("Output:"+output);
						File dir = new File(output);
						if (!dir.exists())
							dir.mkdirs();
						String tagText= subtask[3];
						output += "/" +tagText+"_"+i + extense;
						cmdarray[0] = "/bin/bash";
						cmdarray[1] ="-c";
						cmdarray[2] = Config.path+"WEB-INF/cut.sh";
						cmdarray[2] +=" "+Config.path+ movie_path; // input
						cmdarray[2] +=" "+ subtask[1].replace(',', '.'); // start
						cmdarray[2] +=" "+ subtask[2].replace(',', '.'); // end
						cmdarray[2] +=" "+ output; // tagText
						executeCMD( cmdarray,envp, dir);
						i++;
						DAO.update("insert into tb_clips(tag_name,update_time,clip_file) values(?,now(),?)",tagText,output.substring(Config.path.length()));
						DAO.update("update tb_build_task set finished_task_num=? where id=?",i,build_task_id);
						DAO.update("update tb_build_task set percent=finished_task_num*100/sub_task_num where id=?",build_task_id);
						
					}
					
				}

				public void executeCMD(String[] cmdarray,String[] envp, File dir) {
					System.out.format("%s %s %s\n",cmdarray[0],cmdarray[1],cmdarray[2]);
					try {
						Process proc=Runtime.getRuntime().exec(cmdarray, envp, dir);
						InputStream stdin = proc.getInputStream();
					    InputStreamReader isr = new InputStreamReader(stdin);
					
					        BufferedReader br = new BufferedReader(isr);
					        String line = null;
					     
					        while ((line = br.readLine()) != null)
					            System.out.println(line);
					        System.out.println("");
					        int exitVal = proc.waitFor();
						System.out.println("Process exitValue: " + exitVal);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}).start();
			return "任务启动!";

		} catch (Throwable t) {
			t.printStackTrace();
		}

		return "任务异常！";
	}
}
