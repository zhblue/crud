<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.newsclan.crud.*"%>  
<%@page import="java.io.File"%>  
<%@page import="java.util.UUID"%>  
<%@page import="org.apache.commons.fileupload.FileItem"%>  
<%@page import="java.util.List"%>  
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>  
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>  
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">  
<title>JSP上传文件</title>  
</head>  
<body>   
<%  
int user_id=-1;
int year=Calendar.getInstance().get(Calendar.YEAR);

try{
	user_id=Tools.getUserId(session);
	if(!Auth.canUploadFile(user_id)) return;
}catch(Exception e){return;}

String path = request.getContextPath() ;  
if(ServletFileUpload.isMultipartContent(request)){  
    String type = "";  
    String input_name=request.getParameter("input_name");

    if(request.getParameter("type") != null)//获取文件分类  
        type = request.getParameter("type").toLowerCase() + "/";  
    String callback = request.getParameter("CKEditorFuncNum");//获取回调JS的函数Num  
    FileItemFactory factory = new DiskFileItemFactory();  
    ServletFileUpload servletFileUpload = new ServletFileUpload(factory);  
    servletFileUpload.setHeaderEncoding("UTF-8");//解决文件名乱码的问题  
    List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);  
    for (FileItem item : fileItemsList) {  
        if (!item.isFormField()) {  
        	String fileName = item.getName(); 
            fileName=fileName.replace("..", ".");
            String ext=fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if(".jsp".equals(ext)) continue;
            fileName = "file" + System.currentTimeMillis() + ext;  
            //定义文件路径，根据你的文件夹结构，可能需要做修改  
            String clientPath = Config.get("upload.path")+"/"+year+"/"+user_id +"/"+ type + fileName;  
  	         //保存文件到服务器上  
            File file = new File(pageContext.getServletContext().getRealPath(clientPath));  
            if (!file.getParentFile().exists()) {  
                file.getParentFile().mkdirs();  
            }  
            item.write(file);  
        
            //打印一段JS，调用parent页面的CKEditor的函数，传递函数编号和上传后文件的路径；这句很重要，成败在此一句  
	    if(callback==null){
	        out.println("上传完成！<script type='text/javascript'>window.parent.$('#"+input_name+"').val('"+clientPath+"')</script>");  
	    }else{
            	out.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("+callback+",'"+path+clientPath+"')</script>");  
	    }
            break;  
        }  
    }  
}  
 %>  
</body>  
</html> 
