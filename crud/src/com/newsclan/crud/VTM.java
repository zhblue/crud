package com.newsclan.crud;

import java.util.*;
import java.io.*;
import java.util.zip.*;

public class VTM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String filepath = "demo.srt";
//		readSrtFile(filepath);

		String sql = "select c.id,c.tag_name,m.movie_name,c.start_time,c.end_time,c.clip_file from tb_clips c inner join tb_movie m on c.tb_movie_id=m.id";
		exportClipsBySQL(sql);
		System.exit(0);
	}

	public static void ZipOneFile(String target, String source) {
		FileOutputStream outputStream = null;
		ZipOutputStream zipOutputStream = null;
		FileInputStream input = null;
		try {
			outputStream = new FileOutputStream(target);
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
			input = new FileInputStream(source);

			zipOutputStream.putNextEntry(new ZipEntry(source));

			int readLen = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
				zipOutputStream.write(buffer, 0, readLen);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (zipOutputStream != null)
				zipOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (input != null)
				input.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void ZipFiles(String target, String sources[]) {

		FileOutputStream outputStream = null;
		ZipOutputStream zipOutputStream = null;
		FileInputStream input = null;
		try {
			outputStream = new FileOutputStream(target);
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));

			for (String source : sources) {

				try {
					input = new FileInputStream(source);
					zipOutputStream.putNextEntry(new ZipEntry(source));
					int readLen = 0;
					byte[] buffer = new byte[1 << 20];
					while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
						zipOutputStream.write(buffer, 0, readLen);
					}
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			zipOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (zipOutputStream != null)
				zipOutputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (input != null)
				input.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		final String movie_id = String.valueOf(task.get(0));
		final String movie_path = DAO.queryString("select movie_file from tb_movie where id=?", movie_id);
		String srt = (String) task.get(1);
		final List<String[]> subtasks = parseSRT(srt);
		if (subtasks.size() > 0)
			DAO.update("update tb_build_task set sub_task_num=? where id=?", subtasks.size(), build_task_id);
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int i = 0;
					String start_time, end_time;
					for (String[] subtask : subtasks) {

						String[] envp = new String[] {};
						String[] cmdarray = new String[3];
						System.out.println("movie_path:" + movie_path);
						String extense = movie_path.substring(movie_path.length() - 4);
						String output = Config.path + "output/" + build_task_id;
						System.out.println("Output:" + output);
						File dir = new File(output);
						if (!dir.exists())
							dir.mkdirs();
						String tagText = subtask[3];
						output += "/" + tagText + "_" + i + extense;
						cmdarray[0] = "/bin/bash";
						cmdarray[1] = "-c";
						cmdarray[2] = Config.path + "WEB-INF/cut.sh";
						cmdarray[2] += " " + Config.path + movie_path; // input
						start_time = subtask[1].replace(',', '.');
						end_time = subtask[2].replace(',', '.');
						cmdarray[2] += " " + start_time; // start
						cmdarray[2] += " " + end_time; // end
						cmdarray[2] += " " + output; // tagText
						executeCMD(cmdarray, envp, dir);
						i++;
						List<List> already = DAO.queryList("select id from tb_clips where clip_file=?", false,
								output.substring(Config.path.length()));
						if (already.size() > 0)
							DAO.update(
									"update tb_clips set tag_name=?,update_time=now(),tb_movie_id=?,start_time=?,end_time=? where id=?",
									tagText, movie_id, start_time, end_time, already.get(0).get(0).toString());
						else
							DAO.update(
									"insert into tb_clips(tag_name,update_time,tb_movie_id,start_time,end_time,clip_file)"
											+ " values(?,now(),?,?,?,?)",
									tagText, movie_id, start_time, end_time, output.substring(Config.path.length()));
						DAO.update("update tb_build_task set finished_task_num=? where id=?", i, build_task_id);
						DAO.update("update tb_build_task set percent=finished_task_num*100/sub_task_num where id=?",
								build_task_id);

					}

				}

				public void executeCMD(String[] cmdarray, String[] envp, File dir) {
					System.out.format("%s %s %s\n", cmdarray[0], cmdarray[1], cmdarray[2]);
					try {
						Process proc = Runtime.getRuntime().exec(cmdarray, envp, dir);
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

	public static void exportClipsBySQL(String sql) {
		List<List> data = DAO.queryList(sql, true);
		List title = data.get(0);
		String indexFile = Config.path + "output/" + Tools.today();
		File dir=new File(indexFile);
		if(!dir.exists()) dir.mkdirs();
		final String target=indexFile+"/clips_"+Tools.getRandomSalt()+".zip";
		indexFile+= "/index_" + Tools.getRandomSalt() + ".xml";
		final String [] sources=new String[data.size()];
		try {
			PrintStream out=new PrintStream(indexFile,"UTF-8");
			sources[0]=indexFile;
			System.out.println(indexFile);
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<clips>");
			for (int i = 1; i < data.size(); i++) {
				out.println("<clip>");
				List row = data.get(i);
				for (int j = 0; j < title.size(); j++) {
					out.print("<"+title.get(j)+">");
					out.print(row.get(j));
					out.println("</"+title.get(j)+">");
					if(((String)(title.get(j))).contains("文件")){
						sources[i]=(String) row.get(j);
					}
				}
				out.println("</clip>");
			}
			out.println("</clips>");
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				FileOutputStream outputStream = null;
				ZipOutputStream zipOutputStream = null;
				FileInputStream input = null;
				try {
					outputStream = new FileOutputStream(target);
					zipOutputStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
					boolean first=true;
					for (String source : sources) {

						try {
							
							if(first) {
								input = new FileInputStream(source);
								zipOutputStream.putNextEntry(new ZipEntry("index.xml"));
								first=false;
							}
							else {
								input = new FileInputStream(Config.path+source);
								zipOutputStream.putNextEntry(new ZipEntry(source));
							}
							int readLen = 0;
							byte[] buffer = new byte[1 << 20];
							while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
								zipOutputStream.write(buffer, 0, readLen);
							}
							input.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					zipOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (zipOutputStream != null)
						zipOutputStream.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (input != null)
						input.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
		
	}
}
