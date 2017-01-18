function chkvalue(obj) {
		if(obj.value==null || obj.value==""){
			obj.style.backgroundColor="#FFD9DA";
		}else{
			obj.style.backgroundColor="#ffffff";
		}
}
function writeYears(obj, date){
	var years = date.getYear()+10;
	for (var i=2000; i < years; i++){
		var opt = document.createElement("option");
		opt.text = opt.value = i;
		obj.add(opt);
	}
}
function writeMonths(obj){
	for (var i=1; i<13; i++){
		var opt = document.createElement("option");
		opt.text = opt.value = Math.floor(i/10) ? i + "": "0"+i;
		obj.add(opt);
	}
}
function writeDate(obj, year, month){
	var date = new Date(""+year+"/"+(parseInt(month)+1)+"/"+0).getDate();
	var selectedIndex = (date-1)<obj.selectedIndex ? (date-1) : obj.selectedIndex;
	var len = obj.options.length;
	var i;
	if (date>len){
		for (i=len+1; i<=date; i++){
			var opt = document.createElement("option");
			opt.text = opt.value = Math.floor(i/10) ? i : "0"+i;
			obj.add(opt);
		}
	}else{
		for (i=date; i<len; i++){
			obj.remove(obj.length-1);
		}
		obj.selectedIndex = selectedIndex;
	}
}
function changeStartDate(){
	var start_year = eval("document.forms[0].start_year");
	var start_month = eval("document.forms[0].start_month");
	var start_day = eval("document.forms[0].start_day");
	writeDate(start_day, start_year.options(start_year.selectedIndex).value, start_month.options(start_month.selectedIndex).value);
}
function changeEndDate(){
	var end_year = eval("document.forms[0].end_year");
	var end_month = eval("document.forms[0].end_month");
	var end_day = eval("document.forms[0].end_day");
	writeDate(end_day, end_year.options(end_year.selectedIndex).value, end_month.options(end_month.selectedIndex).value);
}
function getPageSize(){
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all
																			// but
																			// Explorer
																			// Mac
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla
				// and Safari
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	}
	
	//self.innerHeight
	// all except Explorer
	var windowWidth = self.innerWidth;
	var windowHeight = self.innerHeight;
	if (document.documentElement && document.documentElement.clientHeight) { 
		// Explorer 6 Strict Mode
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) { // other Explorers
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	}
    // for small pages with total height less then height of the viewport
	if(yScroll < windowHeight){
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}
    // for small pages with total width less then width of the viewport
	if(xScroll < windowWidth){
		pageWidth = windowWidth;
	} else {
		pageWidth = xScroll;
	}
	arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight);
	return arrayPageSize;
}

function isNum(num){
	for(var i=0;i<num.length;i++){
		if(num.charAt(i)<'0' || num.charAt(i)>'9'){
			return false;
		}
	}
	return true;
}

// 璣ゅ计计翴(.)ρ公(@)┏絬(_)┪绢絬(-)
function isStr(str){
	for(var i=0 ; i < str.length ; i++){
		s = str.charCodeAt(i);
		if(!(s==45 || s==64 || s==95 || s==46 || (s>=48 && s<=57) || (s>=97 && s<=122) || (s>=65 && s<=90))){
			return false;
		}
	}
	return true;
}
//  < > \/"'
function isSubject(str){
	for(var i=0 ; i < str.length ; i++){
		s = str.charCodeAt(i);
		if(s==60 || s==62 || s==47 || s==92 || s==34 || s==39){
			return false;
		}
	}
	return true;
}
// "'
function isContext(str){
	for(var i=0 ; i < str.length ; i++){
		s = str.charCodeAt(i);
		if(s==34){// || s==39){
			return false;
		}
	}
	return true;
}
// 璣ゅ计计翴(.)ρ公(@)┏絬(_)
function isStrNum(str){
	for(var i=0 ; i < str.length ; i++){
		s = str.charCodeAt(i);
		if(!(s==64 || s==95 || s==46 || (s>=48 && s<=57) || (s>=97 && s<=122) || (s>=65 && s<=90))){
			return false;
		}
	}
	return true;
}
// 浪フ
function chkNull(obj,str){
	if(document.getElementById(obj).getAttribute("value") == ""){
		alert(str);
		return false;
	}else{
		return true;	
	}
}
function chkChkedA(obj,str){
	if(document.getElementById(obj).checked == false){
		alert(str);
		return false;
	}else{
		return true;	
	}
}
function chkChked(obj){
	return document.getElementById(obj).checked;
}
// 浪﹃
function chkLen(obj,chklen){
	if(document.getElementById(obj).getAttribute("value").length < chklen){
		return false;
	}else{
		return true;	
	}
}
// 浪琌计
function chkN(obj){
	var str = document.getElementById(obj).getAttribute("value");
	for(var i=0;i<str.length;i++){
		var s = str.charCodeAt(i);
		if(s<48 || s>57){
			return false;
		}
	}
	return true;
}
// 浪琌计┪璣ゅ
function chkNaE(obj){
	var str = document.getElementById(obj).getAttribute("value");
	for(var i=0;i<str.length;i++){
		var s = str.charCodeAt(i);
		if( s < 48 || ( s > 57 && s < 65 ) || ( s > 90 && s < 97 ) || s > 122 ){
			return false;
		}
	}
	return true;
}
// 浪Email
function chkMail(obj){
	var str = document.getElementById(obj).getAttribute("value");
	var k=0;
	for(var i=0;i<str.length;i++){
		var s = str.charCodeAt(i);
		if( s < 45 || ( s > 46 && s < 48) || ( s > 57 && s < 64) || ( s > 90 && s < 95) || ( s > 95 && s < 97) || s > 122 ){
			return false;
		}
		if(s==64){
			k++;
		}
	}
	if(k!=1){
		return false;
	}
	return true;
}
function onlyNumber(obj){
	var oldCont = obj.value;
	var newCont = "";
	for(var i = 0 ; i < oldCont.length ; i++){
		if(oldCont.charAt(i)>='0' && oldCont.charAt(i)<='9'){
			newCont = newCont + oldCont.charAt(i);
		}		
	}
	obj.value = newCont=="" ? "0" : parseInt(newCont,10);
}
function clearZero(obj){
	if(obj.value=="0"){
		obj.value = "";
	}
}
String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); }; 