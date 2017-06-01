	var LODOP;
	function A4Print(){
		//alert(arguments.length);
		var baseline,i;
		var title=new Array("条码","代理号","寄件人姓名","寄件人电话","寄件人地址","收件人姓名","收件人地址","收件人邮编","收件人电话","备注信息","身份证号码","快递单号","内件品名");
		if(LODOP==null) LODOP=getLodop(LODOP1);
		LODOP.PRINT_INIT("A4面单打印");
		LODOP.SET_PRINT_STYLEA(2,"FontName","宋体");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
		for(baseline=0;baseline<1000;baseline+=513){
			LODOP.ADD_PRINT_RECT(baseline,55,600,1,0,1);
			LODOP.ADD_PRINT_IMAGE(baseline+15,15,120,120,"<img border='0' height=120 width=120 src='download/logo.png'>");
			LODOP.ADD_PRINT_BARCODE(baseline+33,314,300,80,"128Auto",arguments[0]);
			for(i=1;i<arguments.length;i++){
				LODOP.ADD_PRINT_TEXT(baseline+126+i*25,41,546,22,title[i]+"："+arguments[i]);
			}
		}
		LODOP.PREVIEW();
	}
	function stickyPrint(){

		LODOP.PRINT_INITA(0,0,370,533,"邮政贴纸");
		LODOP.ADD_PRINT_IMAGE(0,0,115,45,"<img border='0' height=45 width=115 src='download/ce.jpg'>");
		LODOP.SET_SHOW_MODE("BKIMG_WIDTH","100mm");
		LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","150mm");
		LODOP.ADD_PRINT_BARCODE(18,135,220,43,"128Auto",arguments[0]);
		LODOP.ADD_PRINT_RECT(70,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(71,6,163,16,"寄件人/From:"+arguments[2]);
		LODOP.ADD_PRINT_TEXT(71,163,213,20,"电话/Tel:"+arguments[3]);
		LODOP.ADD_PRINT_TEXT(88,6,370,26,"地址/Address:"+arguments[4]);
		LODOP.ADD_PRINT_RECT(114,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(115,6,130,18,"收件人/To:"+arguments[5]);
		LODOP.ADD_PRINT_TEXT(115,163,243,21,"电话/Tel:"+arguments[8]);
		LODOP.ADD_PRINT_TEXT(135,6,360,28,"地址/Address:"+arguments[6]);
		LODOP.ADD_PRINT_TEXT(164,6,156,20,"大客户代码:");
		LODOP.ADD_PRINT_TEXT(164,163,216,20,"邮编/Post Code:"+arguments[7]);
		LODOP.ADD_PRINT_RECT(186,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(188,6,242,75,"内件描述/Name&Description:"+arguments[12]);
		LODOP.ADD_PRINT_TEXT(188,245,126,20,"实际重量:"+arguments[13]+"kg");
		LODOP.ADD_PRINT_RECT(205,230,140,1,0,1);
		LODOP.ADD_PRINT_TEXT(208,242,126,20,"体积重量:"+arguments[13]+"kg");
		LODOP.ADD_PRINT_RECT(230,230,140,1,0,1);
		LODOP.ADD_PRINT_TEXT(236,245,36,20,"长/L");
		LODOP.ADD_PRINT_TEXT(236,278,36,20,"宽/W");
		LODOP.ADD_PRINT_TEXT(236,316,36,20,"高/H");
		LODOP.ADD_PRINT_RECT(264,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(266,6,119,30,"申报价值/Value:"+(arguments[15]== undefined?"":arguments[15]));
		LODOP.ADD_PRINT_TEXT(265,142,97,31,"原产地/Origin");
		LODOP.ADD_PRINT_TEXT(265,245,22,20,"cm");
		LODOP.ADD_PRINT_TEXT(264,278,24,20,"cm");
		LODOP.ADD_PRINT_TEXT(264,316,26,20,"cm");
		LODOP.ADD_PRINT_RECT(311,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(315,6,139,20,"收件人签名：");
		LODOP.ADD_PRINT_TEXT(315,130,219,20,"签收时间         年    月   日   时");
		LODOP.ADD_PRINT_RECT(338,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(341,6,155,20,"寄件人/From:"+arguments[2]);
		LODOP.ADD_PRINT_TEXT(341,168,252,20,"电话/Tel:"+arguments[3]);
		LODOP.ADD_PRINT_TEXT(372,6,369,20,"地址/Address:"+arguments[4]);
		LODOP.ADD_PRINT_RECT(389,5,370,1,0,1);
		LODOP.ADD_PRINT_TEXT(392,6,126,17,"收件人/To:"+arguments[5]);
		LODOP.ADD_PRINT_TEXT(391,163,229,20,"电话/Tel::"+arguments[8]);
		LODOP.ADD_PRINT_TEXT(412,6,360,32,"地址/Address:"+arguments[6]);
		LODOP.ADD_PRINT_RECT(450,5,370,1,0,1);
		LODOP.ADD_PRINT_BARCODE(461,16,199,44,"128Auto",arguments[0]);
		LODOP.ADD_PRINT_TEXT(508,6,100,20,"进口口岸:广州");
		LODOP.ADD_PRINT_TEXT(453,220,130,41,"关联单号:"+arguments[14]);
		LODOP.ADD_PRINT_TEXT(502,220,111,34,"原产地：AU");
		LODOP.PREVIEW();

	}