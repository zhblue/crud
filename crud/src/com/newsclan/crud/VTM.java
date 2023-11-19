package com.newsclan.crud;

import java.util.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.*;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringEscapeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import jxl.write.*;

public class VTM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String filepath = "demo.srt";
		// readSrtFile(filepath);

		// String sql = "select
		// c.id,c.tag_name,m.movie_name,c.start_time,c.end_time,c.clip_file from
		// tb_clips c inner join tb_movie m on c.tb_movie_id=m.id";
		// exportClipsBySQL("report_name",sql);
		System.out.println(timeStampAdd("01:01:00,200", -1000000));
		System.out.println(StringEscapeUtils.escapeJson("'asdf'"));
		System.exit(0);
	}

	public static long TimeStamp2MS(String stamp) {
		long ret = 0l;
		// TODO Auto-generated method stub
		try {
			String s = ".";
			if (stamp.contains(","))
				s = ",";
			String[] tm = stamp.split(s);
			String[] hms = tm[0].split(":");
			int hh = Integer.parseInt(hms[0]);
			int mm = Integer.parseInt(hms[1]);
			int ss = Integer.parseInt(hms[2]);
			int ms = Integer.parseInt(tm[1]);
			ret = hh * 60 + mm;
			ret = ret * 60 + ss;
			ret = ret * 1000 + ms;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String MS2TimeStamp(long in) {
		String ret = "";
		long ms = in % 1000;
		in /= 1000;
		long ss = in % 60;
		in /= 60;
		long mm = in % 60;
		in /= 60;
		long hh = in;
		ret = String.format("%02d:%02d:%02d.%03d", hh, mm, ss, ms);
		return ret;
	}

	public static String timeStampAdd(String timestamp, long ms) {
		long result = TimeStamp2MS(timestamp) + ms;
		if (result < 0)
			result = 0l;
		return MS2TimeStamp(result);
	}

	public static String getTagListOfManualTask(String tbname, int id) {
		StringBuilder sb = new StringBuilder();
		String sql = "select * from `" + tbname + "` where tb_manual_task_id=? order by start_time";
		// sb.append(sql);
		List<List> rows = DAO.queryList(sql, false, Integer.valueOf(id));
		for (List row : rows) {
			sb.append("<tr " + tbname + "_id='" + row.get(0) + "' " + "start_time='" + row.get(2) + "' " + "end_time='"
					+ row.get(3) + "' " + "tagtext='" + row.get(4) + "' x='0' y='0' w='0' h='0' " + ">\n");
			sb.append("<td><button class='btn' onclick='jump($(this).text())'>" + row.get(2) + "</button></td>\n");
			sb.append("<td><button class='btn' onclick='jump($(this).text())'>" + row.get(3) + "</button></td>\n");
			sb.append("<td><button class='btn' onclick='add_tb_data($(this).parent().parent())'>" + row.get(4)
					+ "</button></td>\n");
			sb.append(
					"<td><span class='glyphicon glyphicon-trash' onclick='del_tb_data($(this).parent().parent())'></span></td>\n");
			sb.append(
					"<td><span class='glyphicon glyphicon-repeat' onclick='copy_tb_data($(this).parent().parent())'></span></td>\n");
			sb.append("</tr>");
		}
		return sb.toString();
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

			ObjectMapper mapper = new ObjectMapper();
			SequenceWriter writer = mapper.writerWithDefaultPrettyPrinter().writeValues(System.out);

			writer.write(list);

			for (String[] strings : list) {
				writer.write(strings);
				// for (String string : strings) {
				// System.out.print(string + " ");
				// }
				System.out.println();
			}
			writer.close();

		} catch (IOException e) {
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

	public static void importSRT(int tb_manual_task_id, String srtFile) {
		String tbname = DAO.queryString(
				"select t.table_name from tb_tag_type t inner join tb_manual_task m on t.id=m.tb_tag_type_id and m.id=?",
				tb_manual_task_id);
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(srtFile);
			Scanner input = new Scanner(fis);
			while (input.hasNextLine()) {
				sb.append(input.nextLine());
				sb.append("\n");

			}
			String fieldname = "";
			switch (tbname) {
				case "tb_cam_detail":
					fieldname = "cam_description";
					break;
				case "tb_camara":
					fieldname = "cam_description";
					break;
				case "tb_av_emo_association":
					fieldname = "lines";
					break;
				case "tb_ai_voice":
					fieldname = "lines";
					break;
				default:
					fieldname = "lines";
					return;
			}
			String sql = "insert into " + tbname + " (tb_manual_task_id,start_time,end_time,`" + fieldname
					+ "`) values(?,?,?,?)\n";

			List<String[]> list = parseSRT(sb.toString());
			for (String[] v : list) {
				System.out.format(sql.replaceAll("\\?", "%s"),String.valueOf(tb_manual_task_id),v[1],v[2],v[3]);
				DAO.update(sql, tb_manual_task_id, v[1], v[2], v[3]);
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

	public static List<String[]> parseSRT(String srt) {
		List<String[]> ret = new LinkedList<String[]>();
		StringReader sis = new StringReader(srt);
		Scanner input = new Scanner(sis);
		String num = null;
		String time, times[];
		String tagText, more;
		String area, areas[], XY[], WH[];
		while (input.hasNextLine()) {
			try {
				while ((num = input.nextLine()).equals(""))
					;
				time = input.nextLine();
				times = time.split(" --> ");
				tagText = input.nextLine();
				while (!(more = input.nextLine()).equals(""))
					tagText += more;

				// area = input.nextLine();
				// areas = area.split("-");
				// areas[0] = areas[0].substring(1, areas[0].length() - 1);
				// areas[1] = areas[1].substring(1, areas[1].length() - 1);
				// XY = areas[0].split(",");
				// WH = areas[1].split(",");

				// if (input.hasNextLine())
				// input.nextLine();
				ret.add(new String[] { num, times[0], times[1], tagText });// XY[0],
																			// XY[1],
																			// WH[0],
																			// WH[1]
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println(num);
			}
		}

		sis.close();

		return ret;
	}

	/*
	 * public static String old_build(final Long build_task_id) {
	 * System.out.println("building task:" + build_task_id); String
	 * manual_task_id = DAO.queryString(
	 * "select tb_manual_task_id from tb_build_task where id=?", build_task_id);
	 * List task = DAO.queryList(
	 * "select tb_movie_id,fastsave from tb_manual_task where id=?", false,
	 * manual_task_id) .get(0); final String movie_id =
	 * String.valueOf(task.get(0)); final String movie_path = DAO.queryString(
	 * "select movie_file from tb_movie where id=?", movie_id); String srt =
	 * (String) task.get(1); final List<String[]> subtasks = parseSRT(srt); if
	 * (subtasks.size() > 0) DAO.update(
	 * "update tb_build_task set sub_task_num=? where id=?", subtasks.size(),
	 * build_task_id); try { new Thread(new Runnable() {
	 * 
	 * @Override public void run() { // TODO Auto-generated method stub int i =
	 * 0; String start_time, end_time; for (String[] subtask : subtasks) {
	 * 
	 * String[] envp = new String[] {}; String[] cmdarray = new String[3];
	 * System.out.println("movie_path:" + movie_path); String extense =
	 * movie_path.substring(movie_path.length() - 4); String output =
	 * Config.path + "output/" + build_task_id; System.out.println("Output:" +
	 * output); File dir = new File(output); if (!dir.exists()) dir.mkdirs();
	 * String tagText = subtask[3]; output += "/" + tagText + "_" + i + extense;
	 * cmdarray[0] = "/bin/bash"; cmdarray[1] = "-c"; cmdarray[2] = Config.path
	 * + "WEB-INF/cut.sh"; cmdarray[2] += " " + Config.path + movie_path; //
	 * input start_time = subtask[1].replace(',', '.'); end_time =
	 * subtask[2].replace(',', '.'); cmdarray[2] += " " + start_time; // start
	 * cmdarray[2] += " " + end_time; // end cmdarray[2] += " " + output; //
	 * tagText executeCMD(cmdarray, envp, dir); i++; List<List> already =
	 * DAO.queryList("select id from tb_clips where clip_file=?", false,
	 * output.substring(Config.path.length())); if (already.size() > 0)
	 * DAO.update(
	 * "update tb_clips set tag_name=?,update_time=now(),tb_movie_id=?,start_time=?,end_time=? where id=?"
	 * , tagText, movie_id, start_time, end_time,
	 * already.get(0).get(0).toString()); else DAO.update(
	 * "insert into tb_clips(tag_name,update_time,tb_movie_id,start_time,end_time,clip_file)"
	 * + " values(?,now(),?,?,?,?)", tagText, movie_id, start_time, end_time,
	 * output.substring(Config.path.length())); DAO.update(
	 * "update tb_build_task set finished_task_num=? where id=?", i,
	 * build_task_id); DAO.update(
	 * "update tb_build_task set percent=finished_task_num*100/sub_task_num where id=?"
	 * , build_task_id);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * public void executeCMD(String[] cmdarray, String[] envp, File dir) {
	 * System.out.format("%s %s %s\n", cmdarray[0], cmdarray[1], cmdarray[2]);
	 * try { Process proc = Runtime.getRuntime().exec(cmdarray, envp, dir);
	 * InputStream stdin = proc.getInputStream(); InputStreamReader isr = new
	 * InputStreamReader(stdin);
	 * 
	 * BufferedReader br = new BufferedReader(isr); String line = null;
	 * 
	 * while ((line = br.readLine()) != null) System.out.println(line);
	 * System.out.println(""); int exitVal = proc.waitFor(); System.out.println(
	 * "Process exitValue: " + exitVal);
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 * 
	 * }).start(); return "任务启动!";
	 * 
	 * } catch (Throwable t) { t.printStackTrace(); }
	 * 
	 * return "任务异常！"; }
	 */
	public static String build(final Long build_task_id) {
		System.out.println("building task:" + build_task_id);
		String manual_task_id = DAO.queryString("select tb_manual_task_id from tb_build_task where id=?",
				build_task_id);
		List task = DAO
				.queryList("select tb_movie_id,tb_tag_type_id from tb_manual_task where id=?", false, manual_task_id)
				.get(0);
		final String movie_id = String.valueOf(task.get(0));
		final String movie_path = DAO.queryString("select movie_file from tb_movie where id=?", movie_id);
		Integer tb_tag_type_id = (Integer) task.get(1);
		final String tbname = DAO.queryString("select table_name from tb_tag_type where id=?", tb_tag_type_id);
		String fieldname = "cam_description";
		if (tbname.equals("tb_av_emo_association"))
			fieldname = "lines";
		String sql = "select id,start_time,end_time,`" + fieldname + "` from " + tbname + " where tb_manual_task_id=?";
		final List<List> subtasks = DAO.queryList(sql, false, manual_task_id);
		if (subtasks.size() > 0)
			DAO.update("update tb_build_task set sub_task_num=? where id=?", subtasks.size(), build_task_id);
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int i = 0;
					String start_time, end_time;
					for (List subtask : subtasks) {

						String[] envp = new String[] {};
						String[] cmdarray = new String[3];
						System.out.println("movie_path:" + movie_path);
						String extense = movie_path.substring(movie_path.length() - 4);
						String output = Config.path + "output/" + build_task_id;
						System.out.println("Output:" + output);
						File dir = new File(output);
						if (!dir.exists())
							dir.mkdirs();
						String tagText = (String) subtask.get(3);
						output += "/" + i + extense;
						cmdarray[0] = "/bin/bash";
						cmdarray[1] = "-c";
						cmdarray[2] = Config.path + "WEB-INF/cut.sh";
						cmdarray[2] += " " + Config.path + movie_path; // input
						start_time = timeStampAdd((String) subtask.get(1), -100); // ffmpeg
																					// cut
																					// compensation
						end_time = timeStampAdd((String) subtask.get(2), +100); // ffmpeg
																				// cut
																				// compensation
						cmdarray[2] += " " + start_time; // start
						cmdarray[2] += " " + end_time; // end
						cmdarray[2] += " " + output; // tagText
						executeCMD(cmdarray, envp, dir);
						i++;
						String sql = "update " + tbname + " set clip_file=? where id=?";
						DAO.update(sql, output.substring(Config.path.length()), subtask.get(0));
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

	/**
	 * 插入图片到EXCEL
	 * 
	 * @param picSheet
	 *            sheet
	 * @param pictureFile
	 *            图片file对象
	 * @param cellRow
	 *            行数
	 * @param cellCol
	 *            列数
	 * @throws Exception
	 *             例外
	 */
	public static void addPictureToExcel(WritableSheet picSheet, File pictureFile, int cellRow, int cellCol)
			throws Exception {
		// 开始位置
		double picBeginCol = cellCol - 1;
		double picBeginRow = cellRow - 1;
		// 图片时间的高度，宽度
		double picCellWidth = 0.0;
		double picCellHeight = 0.0;
		// 读入图片
		System.setProperty("java.awt.headless", "true");
		ImageIO.setUseCache(false);
		BufferedImage picImage = ImageIO.read(pictureFile);
		// 取得图片的像素高度，宽度
		int picWidth = picImage.getWidth();
		int picHeight = picImage.getHeight();

		// 计算图片的实际宽度
		int picWidth_t = picWidth * 32; // 具体的实验值，原理不清楚。
		for (int x = 0; x < 1234; x++) {
			int bc = (int) Math.floor(picBeginCol + x);
			// 得到单元格的宽度
			int v = picSheet.getColumnView(bc).getSize();
			double offset0_t = 0.0;
			if (0 == x)
				offset0_t = (picBeginCol - bc) * v;
			if (0.0 + offset0_t + picWidth_t > v) {
				// 剩余宽度超过一个单元格的宽度
				double ratio_t = 1.0;
				if (0 == x) {
					ratio_t = (0.0 + v - offset0_t) / v;
				}
				picCellWidth += ratio_t;
				picWidth_t -= (int) (0.0 + v - offset0_t);
			} else { // 剩余宽度不足一个单元格的宽度
				double ratio_r = 0.0;
				if (v != 0)
					ratio_r = (0.0 + picWidth_t) / v;
				picCellWidth += ratio_r;
				break;
			}
		}
		// 计算图片的实际高度
		int picHeight_t = picHeight * 15;
		for (int x = 0; x < 1234; x++) {
			int bc = (int) Math.floor(picBeginRow + x);
			// 得到单元格的高度
			int v = picSheet.getRowView(bc).getSize();
			double offset0_r = 0.0;
			if (0 == x)
				offset0_r = (picBeginRow - bc) * v;
			if (0.0 + offset0_r + picHeight_t > v) {
				// 剩余高度超过一个单元格的高度
				double ratio_q = 1.0;
				if (0 == x)
					ratio_q = (0.0 + v - offset0_r) / v;
				picCellHeight += ratio_q;
				picHeight_t -= (int) (0.0 + v - offset0_r);
			} else {// 剩余高度不足一个单元格的高度
				double ratio_m = 0.0;
				if (v != 0)
					ratio_m = (0.0 + picHeight_t) / v;
				picCellHeight += ratio_m;
				break;
			}
		}
		// 生成一个图片对象。
		WritableImage image = new WritableImage(picBeginCol, picBeginRow, picCellWidth, picCellHeight, pictureFile);
		// 把图片插入到sheet
		picSheet.addImage(image);
	}

	public static void exportClipsBySQL(final String name, String sql) {
		List<List> data = DAO.queryList(sql, true);
		List title = data.get(0);
		String indexFile = Config.path + "output/" + Tools.today();
		File dir = new File(indexFile);
		if (!dir.exists())
			dir.mkdirs();
		final String target = indexFile + "/clips_" + Tools.getRandomSalt() + ".zip";
		indexFile += "/index_" + Tools.getRandomSalt() + ".json";
		final String[] sources = new String[data.size()];
		try {
			PrintStream out = new PrintStream(indexFile, "UTF-8");

			ObjectMapper mapper = new ObjectMapper();
			SequenceWriter writer = mapper.writerWithDefaultPrettyPrinter().writeValues(out);
			sources[0] = indexFile;
			System.out.println(indexFile);
			writer.write(data);

			// out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			// out.println("<clips>");
			for (int i = 1; i < data.size(); i++) {
				// out.println("<clip>");
				List row = data.get(i);
				for (int j = 0; j < title.size(); j++) {
					// out.print("<"+title.get(j)+">");
					// out.print(row.get(j));
					// out.println("</"+title.get(j)+">");
					if (String.valueOf(title.get(j)).contains("文件")) {
						sources[i] = (String) row.get(j);
					}
				}
				// out.println("</clip>");
			}
			// out.println("</clips>");
			writer.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
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
					boolean first = true;
					for (String source : sources) {

						try {

							if (first) {
								input = new FileInputStream(source);
								zipOutputStream.putNextEntry(new ZipEntry("index.json"));
								first = false;
							} else {
								input = new FileInputStream(Config.path + source);
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
					DAO.update("insert into tb_download_zip(update_time,report_name,zip_file) values(now(),?,?)", name,
							target.substring(Config.path.length()));
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

	public static void exportActorsBySQL(final String name, String sql) {
		String objectName = "actor";
		List<List> data = DAO.queryList(sql, true);
		List title = data.get(0);
		String indexFile = Config.path + "output/" + Tools.today();
		File dir = new File(indexFile);
		if (!dir.exists())
			dir.mkdirs();
		final String target = indexFile + "/" + objectName + "_" + Tools.getRandomSalt() + ".zip";
		indexFile += "/index_" + Tools.getRandomSalt() + ".xml";
		final List<String> sources = new LinkedList();
		try {
			PrintStream out = new PrintStream(indexFile, "UTF-8");
			sources.add(indexFile);
			System.out.println(indexFile);
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<" + objectName + "s>");
			for (int i = 1; i < data.size(); i++) {
				out.println("<" + objectName + ">");
				List row = data.get(i);
				for (int j = 0; j < title.size(); j++) {
					out.print("<" + title.get(j) + ">");
					out.print(row.get(j));
					out.println("</" + title.get(j) + ">");
					if (((String) (title.get(j))).contains("照片")) {
						sources.add((String) row.get(j));
					}
				}
				out.println("</" + objectName + ">");
			}
			out.println("</" + objectName + "s>");

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
					boolean first = true;
					for (String source : sources) {

						try {

							if (first) {
								input = new FileInputStream(source);
								zipOutputStream.putNextEntry(new ZipEntry("index.xml"));
								first = false;
							} else {
								input = new FileInputStream(Config.path + source);
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
					DAO.update("insert into tb_download_zip(update_time,report_name,zip_file) values(now(),?,?)", name,
							target.substring(Config.path.length()));
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
