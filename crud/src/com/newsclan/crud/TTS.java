package com.newsclan.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TTS implements Runnable{
	Map para;
	String targetFile;
	String text;
	private Long id;
	public TTS(String text, String targetFile, Map para,Long id) {
		// TODO Auto-generated constructor stub
		this.text=text;
		this.targetFile=targetFile;
		this.para=para;
		this.id=id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TTS.tts(text, targetFile, para);
		System.out.println(targetFile);
		DAO.update("update tb_tts set wav_file=?,done=50 where id=?", targetFile.substring(Config.path.length()),id);
		
		String[] envp = new String[] {};
		String[] cmdarray = new String[3];
		System.out.println("wav_path:" + targetFile);
		String extense = targetFile.substring(targetFile.length() - 4);
		String output = Config.path + "output/" +Tools.today();
		System.out.println("Output:" + output);
		File dir = new File(output);
		if (!dir.exists())
			dir.mkdirs();
		String name=DAO.queryString("select name from tb_tts where id=?", id);
		
		output += "/" + id + ".mp3";
		cmdarray[0] = "/bin/bash";
		cmdarray[1] = "-c";
		cmdarray[2] = Config.path + "WEB-INF/mp3.sh";
		cmdarray[2] += " " + targetFile; // input
		cmdarray[2] += " " + output; // mp3
		Tools.executeCMD(cmdarray, envp, dir);
		DAO.update("update tb_tts set mp3_file=?,done=100 where id=?", output.substring(Config.path.length()),id);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map para=new HashMap();
		//para.put("read_number", "1");
		String text=Tools.getFileContent("mum.txt");
		TTS.tts(text,"test.wav",para);
	}
	public static void buildTTS(Long id){
		String targetFile=Config.path+"output/"+Tools.today();
		File dir=new File(targetFile);
		if(!dir.exists()) dir.mkdirs();
		targetFile+="/"+id+".wav";
		Map para=new HashMap();
		String text=DAO.queryString("select content from tb_tts where id=?", id);
		String speed=DAO.queryString("select speed from tb_tts where id=?", id);
		para.put("speed", speed);
		para.put("volume", "10");
		para.put("read_number", "3");
		new Thread(new TTS(text,targetFile,para,id)).start();	
	}
	public static void buildAIVoice(Long id){
		System.out.println("Starting working on "+id);
		new Thread(new AIVoiceRunner(id)).start();
	}
	public static void tts(String text, String file, Map para) {
		System.out.println("wav file:"+file);
		// TODO Auto-generated method stub
		WavFileWriter wavFileWriter = new WavFileWriter();
		wavFileWriter.open(file, 16000, (short) 1);
	
		String[] msgs = text.split("\n");
		int i=0;
		for(String msg:msgs){
			msg=msg.trim();
			if(!msg.equals("")){
				i++;
				List <byte[]>line=ttsShort(msg,para);
				for (byte[] d : line) {
					wavFileWriter.writeData(d);
				}
			}	
		}
		wavFileWriter.close();
	}

	private static List<byte[]> ttsShort(String text,Map para){
		String url = Config.get("ifly.tts")+"/createRec";//"http://10.1.251.5:10011/createRec";
		if(!para.containsKey("sid"))
			try {
				para.put("sid", "["+InetAddress.getLocalHost().getHostName()+"]" +Tools.now()+String.format("%4.0f", Math.random()*1000) );
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		ObjectMapper om = new ObjectMapper();
		String raw="";
		
		try {
			//System.out.println(om.writeValueAsString(para));
			raw = "{" + "\"sessionParam\":"+om.writeValueAsString(para)+"," 
			          + "\"text\":\""+text.replace("\"", "\\\"")+"\",\"endFlag\":true}";
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = Tools.http_post_raw(url, raw);
		//System.out.println(response);
		List<byte[]> rawData = Tools.getRawData(response);
		return rawData;
	}

	private static void writeFile(String file, List<byte[]> rawData) {
		// TODO Auto-generated method stub
		WavFileWriter wavFileWriter = new WavFileWriter();
		wavFileWriter.open(file, 16000, (short) 1);
		for (byte[] d : rawData) {
			wavFileWriter.writeData(d);
		}
		wavFileWriter.close();
	
	}
	
}
