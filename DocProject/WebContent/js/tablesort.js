var dom = (document.getElementsByTagName) ? true : false;
var ie5 = (document.getElementsByTagName && document.all) ? true : false;
var arrowUp, arrowDown;

if (ie5 || dom){
	initSortTable();
}
	

function initSortTable() {
	arrowUp = document.createElement("SPAN");
	var tn = document.createTextNode("");
	arrowUp.appendChild(tn);
	arrowUp.className = "arrow";

	arrowDown = document.createElement("SPAN");
	var tn = document.createTextNode("");
	arrowDown.appendChild(tn);
	arrowDown.className = "arrow";
}



function sortTable(tableNode, nCol, bDesc, sType) {
//bDesc  告訴現在要順排或逆排的 flah 
//sType  哪種資料型態
// nCol   找出那個col 有幾個
//tableNode  就是那個 table

	var tBody = tableNode.tBodies[0];
	var trs = tBody.rows;

	var array = new Array();
	//多用一個array 才會排序
	
	for (var i = 0; i < trs.length ; i++) {
		array[i] = trs[i];
	}
	
	var start = new Date;

	array.sort(compareByColumn(nCol,bDesc,sType));

	for (var i = 0; i < trs.length ; i++) {  //這邊在做排序
		tBody.appendChild(array[i]);
	}
	
}

function parseDate(s) {
	
	return Date.parse(s.substring(0,10).replace(/\-/g, '/'));
	 //把 日期轉成這樣 就可以比較了
}


function compareByColumn(nCol, bDescending, sType) {

//bDesc  告訴現在要順排或逆排的 flah 
//sType  哪種資料型態
// nCol   找出那個col 有幾個
	
	var fTypeCast = parseDate;

	//排序文字
	return function (n1, n2) {
		if (fTypeCast(getInnerText(n1.cells[nCol])) < fTypeCast(getInnerText(n2.cells[nCol])))
			return bDescending ? -1 : +1;
		if (fTypeCast(getInnerText(n1.cells[nCol])) > fTypeCast(getInnerText(n2.cells[nCol])))
			return bDescending ? +1 : -1;
		return 0;
	};
}

//從這邊近來
function sortColumn(event) {
	//event 是 event

	if (event.target){
		var tmp =  event.target ;
	}
	else {
		var tmp =  event.srcElement;
	}
	//找Table 裡面的  Tag
	
	var tHeadParent = getParent(tmp, "THEAD");		
	
	var thTag = getParent(tmp, "TH");

	if (tHeadParent == null){   //沒有頭
		return;
	}

	if (thTag != null) {
		var thParent = thTag.parentNode;

		// typecast to Boolean
		thTag._descending = !Boolean(thTag._descending);
			
		if (tHeadParent.arrow != null) {
			if (tHeadParent.arrow.parentNode != thTag) {
				tHeadParent.arrow.parentNode._descending = null;	//reset sort order		
			}
			tHeadParent.arrow.parentNode.removeChild(tHeadParent.arrow);
			//先移除 tHeadParent 的孩子  也就是下面的標籤
		}

		if (thTag._descending){
			tHeadParent.arrow = arrowUp.cloneNode(true);
		}		
		else{
			tHeadParent.arrow = arrowDown.cloneNode(true);
		}
		thTag.appendChild(tHeadParent.arrow);
		//然後在增加上去
			

		// get the index of the td
		var cells = thParent.cells;  //找出那個td 的元素
		var i;
		
		for (i = 0; i < cells.length; i++) {
			if (cells[i] == thTag) break;
		}

		var table = getParent(thTag, "TABLE");
		// can't fail
		
		sortTable(table , i , thTag._descending ,  thTag.getAttribute("type")  );
	}
}

function getInnerText(thTag) {
	var str = "";
	
	var cs = thTag.childNodes;
	for (var i = 0; i < cs.length ; i++) {
		switch (cs[i].nodeType) {
			case 3:	//TEXT_NODE
				str += cs[i].nodeValue;
				break;
		}		
	}	
	return str;
}

function getParent(thTag, pTagName) {
	if (thTag == null) return null;
	else if (thTag.nodeType == 1 && thTag.tagName.toLowerCase() == pTagName.toLowerCase())	
		return thTag;
	else
		return getParent(thTag.parentNode, pTagName);
}