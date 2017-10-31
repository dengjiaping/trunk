
package com.histudent.jwsoft.histudent.commen.keyword.utils;

import android.support.v4.util.ArrayMap;

import com.histudent.jwsoft.histudent.R;


/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

    /**
     * 表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE = 0x0001;//经典表情

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMPTY_MAP;
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


    static {
        EMPTY_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP = new ArrayMap<>();

        EMOTION_CLASSIC_MAP.put("[委屈]", R.drawable.wypp0);
        EMOTION_CLASSIC_MAP.put("[遭雷击了]", R.drawable.wypp1);
        EMOTION_CLASSIC_MAP.put("[大叫]", R.drawable.wypp2);
        EMOTION_CLASSIC_MAP.put("[愤怒]", R.drawable.wypp3);
        EMOTION_CLASSIC_MAP.put("[小鼻孔]", R.drawable.wypp4);
        EMOTION_CLASSIC_MAP.put("[擦汗]", R.drawable.wypp5);
        EMOTION_CLASSIC_MAP.put("[撇嘴]", R.drawable.wypp6);
        EMOTION_CLASSIC_MAP.put("[挨板砖]", R.drawable.wypp7);
        EMOTION_CLASSIC_MAP.put("[受伤]", R.drawable.wypp8);
        EMOTION_CLASSIC_MAP.put("[炸弹]", R.drawable.wypp9);
        EMOTION_CLASSIC_MAP.put("[脸红]", R.drawable.wypp10);
        EMOTION_CLASSIC_MAP.put("[色]", R.drawable.wypp11);
        EMOTION_CLASSIC_MAP.put("[牙缝里有菜叶]", R.drawable.wypp12);
        EMOTION_CLASSIC_MAP.put("[装酷]", R.drawable.wypp13);
        EMOTION_CLASSIC_MAP.put("[烧香保佑]", R.drawable.wypp14);
        EMOTION_CLASSIC_MAP.put("[拜拜]", R.drawable.wypp15);
        EMOTION_CLASSIC_MAP.put("[藐视]", R.drawable.wypp16);
        EMOTION_CLASSIC_MAP.put("[发怒]", R.drawable.wypp17);
        EMOTION_CLASSIC_MAP.put("[寒]", R.drawable.wypp18);
        EMOTION_CLASSIC_MAP.put("[装老大]", R.drawable.wypp19);
        EMOTION_CLASSIC_MAP.put("[想哭]", R.drawable.wypp20);
        EMOTION_CLASSIC_MAP.put("[强]", R.drawable.wypp21);
        EMOTION_CLASSIC_MAP.put("[泪流满面]", R.drawable.wypp22);
        EMOTION_CLASSIC_MAP.put("[翻白眼]", R.drawable.wypp23);
        EMOTION_CLASSIC_MAP.put("[哇美女]", R.drawable.wypp24);
        EMOTION_CLASSIC_MAP.put("[害羞]", R.drawable.wypp25);
        EMOTION_CLASSIC_MAP.put("[眼睛喷血]", R.drawable.wypp26);
        EMOTION_CLASSIC_MAP.put("[微笑]", R.drawable.wypp27);
        EMOTION_CLASSIC_MAP.put("[晕了]", R.drawable.wypp28);
        EMOTION_CLASSIC_MAP.put("[憨笑]", R.drawable.wypp29);
        EMOTION_CLASSIC_MAP.put("[恶魔]", R.drawable.wypp30);
        EMOTION_CLASSIC_MAP.put("[吃饭]", R.drawable.wypp31);
        EMOTION_CLASSIC_MAP.put("[偷乐]", R.drawable.wypp32);
        EMOTION_CLASSIC_MAP.put("[扮酷]", R.drawable.wypp33);
        EMOTION_CLASSIC_MAP.put("[白眼]", R.drawable.wypp34);
        EMOTION_CLASSIC_MAP.put("[吹鼻泡]", R.drawable.wypp35);
        EMOTION_CLASSIC_MAP.put("[大惊]", R.drawable.wypp36);
        EMOTION_CLASSIC_MAP.put("[不信]", R.drawable.wypp37);
        EMOTION_CLASSIC_MAP.put("[偷看]", R.drawable.wypp38);
        EMOTION_CLASSIC_MAP.put("[yoyo]", R.drawable.wypp39);
        EMOTION_CLASSIC_MAP.put("[尴尬]", R.drawable.wypp40);
        EMOTION_CLASSIC_MAP.put("[流鼻血]", R.drawable.wypp41);
        EMOTION_CLASSIC_MAP.put("[鄙视]", R.drawable.wypp42);
        EMOTION_CLASSIC_MAP.put("[可爱]", R.drawable.wypp43);
        EMOTION_CLASSIC_MAP.put("[蜘蛛侠]", R.drawable.wypp44);
        EMOTION_CLASSIC_MAP.put("[流口水]", R.drawable.wypp45);
        EMOTION_CLASSIC_MAP.put("[哭]", R.drawable.wypp46);

        EMOTION_CLASSIC_MAP.put("[高兴]", R.drawable.wypp47);
        EMOTION_CLASSIC_MAP.put("[流汗]", R.drawable.wypp48);
        EMOTION_CLASSIC_MAP.put("[亲吻]", R.drawable.wypp49);
        EMOTION_CLASSIC_MAP.put("[困]", R.drawable.wypp50);
        EMOTION_CLASSIC_MAP.put("[流泪]", R.drawable.wypp51);
        EMOTION_CLASSIC_MAP.put("[惊讶]", R.drawable.wypp52);
        EMOTION_CLASSIC_MAP.put("[怒视]", R.drawable.wypp53);
//        EMOTION_CLASSIC_MAP.put("[删除]", R.drawable.delete);
    }

    /**
     * 根据名称获取当前表情图标R值
     *
     * @param EmotionType 表情类型标志符
     * @param imgName     名称
     * @return
     */
    public static int getImgByName(int EmotionType, String imgName) {
        Integer integer = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
            default:
                LogUtils.e("the emojiMap is null!!");
                break;
        }
        return integer == null ? -1 : integer;
    }

    /**
     * 根据类型获取表情数据
     *
     * @param EmotionType
     * @return
     */
    public static ArrayMap<String, Integer> getEmojiMap(int EmotionType) {
        ArrayMap EmojiMap = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                EmojiMap = EMOTION_CLASSIC_MAP;
                break;
            default:
                EmojiMap = EMPTY_MAP;
                break;
        }
        return EmojiMap;
    }
}
