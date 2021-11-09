package com.newsclan.crud;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	public static List<String[]> parseSRT(String srt) {
		List<String[]> ret = new LinkedList<String[]>();
		StringReader sis = new StringReader(srt);
		Scanner input = new Scanner(sis);

		String num = null;
		String time, times[];
		String tagText;
		String area, areas[];
		while (input.hasNextLine()) {
			try {
				num = input.nextLine();
				time = input.nextLine();
				times = time.split(" --> ");
				tagText = input.nextLine();
				area = input.nextLine();
				areas = area.split("-");
				if (input.hasNextLine()) input.nextLine();
				ret.add(new String[] { num, times[0], times[1], tagText, areas[0], areas[1] });
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(num);
			}
		}

		sis.close();

		return ret;
	}

}
