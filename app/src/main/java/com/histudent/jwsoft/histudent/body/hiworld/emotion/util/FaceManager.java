package com.histudent.jwsoft.histudent.body.hiworld.emotion.util;

import com.histudent.jwsoft.histudent.R;
import com.netease.nim.uikit.common.util.string.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author tracyZhang  https://github.com/TracyZhangLei
 * @since  2014-4-4
 *
 */
public class FaceManager {

	private static String HeaderStr="卍йЪ";
	private static String DeleteStr="[删除]";
	
	private FaceManager(){
		initFaceMap();
	}
	
	private static FaceManager instance;

	public static FaceManager getInstance() {
		if(null == instance)
			instance = new FaceManager();
		return instance;
	}
	
	private Map<String, Integer> mFaceMap;
	private List<EmotionBean>list;
	
	private void initFaceMap() {
		mFaceMap = new LinkedHashMap<String, Integer>();
		mFaceMap.put("[委屈]", R.drawable.wypp0);
		mFaceMap.put("[遭雷击了]", R.drawable.wypp1);
		mFaceMap.put("[大叫]", R.drawable.wypp2);
		mFaceMap.put("[愤怒]", R.drawable.wypp3);
		mFaceMap.put("[小鼻孔]", R.drawable.wypp4);
		mFaceMap.put("[擦汗]", R.drawable.wypp5);
		mFaceMap.put("[撇嘴]", R.drawable.wypp6);
		mFaceMap.put("[挨板砖]", R.drawable.wypp7);
		mFaceMap.put("[受伤]", R.drawable.wypp8);
		mFaceMap.put("[炸弹]", R.drawable.wypp9);
		mFaceMap.put("[脸红]", R.drawable.wypp10);
		mFaceMap.put("[色]", R.drawable.wypp11);
		mFaceMap.put("[牙缝里有菜叶]", R.drawable.wypp12);
		mFaceMap.put("[装酷]", R.drawable.wypp13);
		mFaceMap.put("[烧香保佑]", R.drawable.wypp14);
		mFaceMap.put("[拜拜]", R.drawable.wypp15);
		mFaceMap.put("[藐视]", R.drawable.wypp16);
		mFaceMap.put("[发怒]", R.drawable.wypp17);
		mFaceMap.put("[寒]", R.drawable.wypp18);
		mFaceMap.put("[装老大]", R.drawable.wypp19);
		mFaceMap.put("[想哭]", R.drawable.wypp20);
		mFaceMap.put("[强]", R.drawable.wypp21);
		mFaceMap.put("[泪流满面]", R.drawable.wypp22);
		mFaceMap.put("[翻白眼]", R.drawable.wypp23);
		mFaceMap.put("[哇美女]", R.drawable.wypp24);
		mFaceMap.put("[害羞]", R.drawable.wypp25);
		mFaceMap.put("[眼睛喷血]", R.drawable.wypp26);
		mFaceMap.put("[微笑]", R.drawable.wypp27);
		mFaceMap.put("[晕了]", R.drawable.wypp28);
		mFaceMap.put("[憨笑]", R.drawable.wypp29);
		mFaceMap.put("[恶魔]", R.drawable.wypp30);
		mFaceMap.put("[吃饭]", R.drawable.wypp31);
		mFaceMap.put("[偷乐]", R.drawable.wypp32);
		mFaceMap.put("[扮酷]", R.drawable.wypp33);
		mFaceMap.put("[白眼]", R.drawable.wypp34);
		mFaceMap.put("[吹鼻泡]", R.drawable.wypp35);
		mFaceMap.put("[大惊]", R.drawable.wypp36);
		mFaceMap.put("[不信]", R.drawable.wypp37);
		mFaceMap.put("[偷看]", R.drawable.wypp38);
		mFaceMap.put("[yoyo]", R.drawable.wypp39);
		mFaceMap.put("[尴尬]", R.drawable.wypp40);
		mFaceMap.put("[流鼻血]", R.drawable.wypp41);
		mFaceMap.put("[鄙视]", R.drawable.wypp42);
		mFaceMap.put("[可爱]", R.drawable.wypp43);
		mFaceMap.put("[蜘蛛侠]", R.drawable.wypp44);
		mFaceMap.put("[流口水]", R.drawable.wypp45);
		mFaceMap.put("[哭]", R.drawable.wypp46);

		mFaceMap.put("[高兴]", R.drawable.wypp47);
		mFaceMap.put("[流汗]", R.drawable.wypp48);
		mFaceMap.put("[亲吻]", R.drawable.wypp49);
		mFaceMap.put("[困]", R.drawable.wypp50);
		mFaceMap.put("[流泪]", R.drawable.wypp51);
		mFaceMap.put("[惊讶]", R.drawable.wypp52);
		mFaceMap.put("[怒视]", R.drawable.wypp53);
		mFaceMap.put("[删除]",R.drawable.delete);

	}
	
	public int getFaceId(String faceStr){
		if(mFaceMap.containsKey(faceStr)){
			return mFaceMap.get(faceStr);
		}
		return -1;
	}

	public  List<EmotionBean> getList(){

		list=new ArrayList<>();
		EmotionBean emotionBean;
		 Set<Map.Entry<String,Integer>> set= mFaceMap.entrySet();
		Iterator<Map.Entry<String,Integer>> iterator=set.iterator();
		while (iterator.hasNext()){
			Map.Entry<String,Integer> entry=iterator.next();
			emotionBean=new EmotionBean();
			emotionBean.setName(entry.getKey());
			emotionBean.setResId(entry.getValue());
			list.add(emotionBean);
		}
		return this.list;
	}

	public static String getHeaderStr(){
		return HeaderStr;
	}
	public static String Delete(){
		return DeleteStr;
	}


	//除去表情符号中的混淆符号

	public String  deleteEmotionConfuseContent(String content) {

		if (!StringUtil.isEmpty(content)) {
			String content2;
			if (content.contains(HeaderStr)){
				content2= content.replaceAll(HeaderStr,"");
				return content2;
			}
			return content;
		}
		return content;
	}

}
