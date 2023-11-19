package com.newsclan.crud;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIVoiceRunner implements Runnable {
	Long tb_manual_task_id;
	public AIVoiceRunner(Long tb_manual_task_id) {
		// TODO Auto-generated constructor stub
		this.tb_manual_task_id=tb_manual_task_id;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		runAIVoice(tb_manual_task_id);
	}
	 public static void runAIVoice(Long tb_manual_task_id){
		    
	    	String targetDir=Config.path+"output/"+Tools.today();
			File dir=new File(targetDir);
			if(!dir.exists()) dir.mkdirs();
			String[] envp = new String[] {};
			String[] cmdarray = new String[3];
			cmdarray[0] = "/bin/bash";
			cmdarray[1] = "-c";
			List<List> movie=DAO.queryList("select m.id,m.movie_file from tb_movie m inner join tb_manual_task mt on m.id=mt.tb_movie_id and mt.id=?",false, tb_manual_task_id);
			String movie_id=String.valueOf(movie.get(0).get(0));
			String movie_file=Config.path+String.valueOf(movie.get(0).get(1));
			String original_file=movie_file;
			Map para=new HashMap();
			para.put("speed", "-10" );
			List<List> lines=DAO.queryList("select id,`lines`,start_time from tb_ai_voice where tb_manual_task_id=? order by id ",false, tb_manual_task_id);
			for(List line:lines){
				String id=String.valueOf(line.get(0));
				while(id.length()<3) id="0"+id;
				String text=(String) line.get(1);
				String start_time=(String)line.get(2);
				start_time=start_time.replace(",", ".");
				String targetFile=targetDir+"/ai_voice_"+id+".wav";
				String mp3File=targetDir+"/ai_voice_"+id+".mp3";
				String newFile=targetDir+"/ai_voice_"+id+".mp4";
				
				TTS.tts(text, targetFile, para);
				cmdarray[2] = Config.path + "WEB-INF/mp3.sh";
				cmdarray[2] += " " + targetFile; // input
				cmdarray[2] += " " + mp3File; // mp3
				Tools.executeCMD(cmdarray, envp, dir);
				DAO.update("update tb_ai_voice set clip_file=? where id=?", mp3File.substring(Config.path.length()),id);
				attachVoice(movie_file,start_time,mp3File,newFile);
				if(!original_file.equals(movie_file)) {
					new File(movie_file).delete();
				}
				movie_file=newFile;
			}
			DAO.update("update tb_manual_task set output_file=? where id=", movie_file.substring(Config.path.length()),tb_manual_task_id);
	    }
	private static String attachVoice(String movie_file, String start_time, String mp3File, String newFile) {
		// TODO Auto-generated method stub
		String[] envp = new String[] {};
		String[] cmdarray = new String[3];
		cmdarray[0] = "/bin/bash";
		cmdarray[1] = "-c";
		String targetDir=Config.path+"output/"+Tools.today();
		File dir=new File(targetDir);
		if(!dir.exists()) dir.mkdirs();
		cmdarray[2] = Config.path + "WEB-INF/attach.sh";
		cmdarray[2] += " " + movie_file; // input
		cmdarray[2] += " " + start_time; // input
		cmdarray[2] += " " + mp3File; // mp3
		cmdarray[2] += " " + newFile; // input
		Tools.executeCMD(cmdarray, envp, dir);
		return newFile;
	}
}
