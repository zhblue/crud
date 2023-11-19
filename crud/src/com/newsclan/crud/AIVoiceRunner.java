package com.newsclan.crud;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
			para.put("speed", "60");
			para.put("volume", "10");
			para.put("read_number", "3");
			List<String> time_list=new Vector();
			List<String> wav_list=new Vector();
			String newFile=targetDir+"/ai_voice_"+tb_manual_task_id+".mp4";
			
			List<List> lines=DAO.queryList("select id,`lines`,start_time from tb_ai_voice where tb_manual_task_id=? order by id ",false, tb_manual_task_id);
			for(List line:lines){
				String id=String.valueOf(line.get(0));
				while(id.length()<3) id="0"+id;
				String text=(String) line.get(1);
				String start_time=(String)line.get(2);
				

				String targetFile=targetDir+"/ai_voice_"+id+".wav";
				String mp3File=targetDir+"/ai_voice_"+id+".mp3";
				if(!new File(mp3File).exists()){
					TTS.tts(text, targetFile, para);
					cmdarray[2] = Config.path + "WEB-INF/mp3m.sh";
					cmdarray[2] += " " + targetFile; // input
					cmdarray[2] += " " + mp3File; // mp3
					Tools.executeCMD(cmdarray, envp, dir);
					DAO.update("update tb_ai_voice set clip_file=? where id=?", mp3File.substring(Config.path.length()),id);
				}
				time_list.add(start_time);
				wav_list.add(mp3File);
				
			}
			attachVoice(movie_file,time_list,wav_list,newFile);
			
			DAO.update("update tb_manual_task set output_file=? where id=?", newFile.substring(Config.path.length()),tb_manual_task_id);
	    }
	private static String attachVoice(String movie_file, List<String> start_time, List<String> mp3File, String newFile) {
		// TODO Auto-generated method stub
		String[] envp = new String[] {};
		String[] cmdarray = new String[2];
		cmdarray[0] = "/bin/bash";
		//cmdarray[1] = "-c";
		String targetDir=Config.path+"output/"+Tools.today();
		File dir=new File(targetDir);
		if(!dir.exists()) dir.mkdirs();
		cmdarray[1] = Config.path + "WEB-INF/attach_"+Tools.today()+Math.random()+".sh";
		System.out.format("execute:%s %s\n",cmdarray[0] ,cmdarray[1]);
		Tools.writeFile(cmdarray[1],"/usr/bin/ffmpeg -loglevel quiet "+videoAddWav(movie_file,start_time,mp3File,newFile));
		Tools.executeCMD(cmdarray, envp, dir);
		return newFile;
	}
	public static void main(String [] args){
		List<String> time_list=new Vector();
		List<String> wav_list=new Vector();
		time_list.add("00:00:04.656");
		wav_list.add("ai_voice_003.mp3");
		videoAddWav("../../upload/2023/7/file1700297873379.mp4",time_list,wav_list,"ai_voice_003.mp4");
	}
	public static String videoAddWav(String mp4Path, List<String>time_list,List<String> wav_list,String newFile) {
		
        StringBuffer cmd = new StringBuffer("-y -i "+mp4Path);
        StringBuffer cmd2 = new StringBuffer(" -filter_complex \'");
        StringBuffer cmd3 = new StringBuffer("[0]");
        for(int i=0;i<wav_list.size();i++){
            String wav = wav_list.get(i);
            cmd.append(" -i "+wav);
            long timeOut=VTM.TimeStamp2MS(time_list.get(i));
            cmd2.append("["+(i+1)+"]adelay="+timeOut+"[a"+(i+1)+"];");
            cmd3.append("[a"+(i+1)+"]");
        }
        cmd3.append("amix="+(wav_list.size()+1)+"\'");
        cmd2.append(cmd3);
        cmd.append(cmd2);
        cmd.append(" -c:v copy "+newFile);
        System.out.println(cmd);
        return cmd.toString();
    }

}
