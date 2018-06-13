package com.gray.newreaderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gray.newreaderview.reader.adapter.MyReaderAdapter;
import com.gray.newreaderview.reader.bean.ChaptersBean;
import com.gray.newreaderview.reader.draw.RealMoveDraw;
import com.gray.newreaderview.reader.draw.VerticalMoveDraw;
import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.view.ReaderView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String word = "　一夜，天还没亮，屋里漆黑一片，只有落地花罩外一盏小小的宫灯正发着微弱的黄光。\n" +
            "\n" +
            "　　\n" +
            "\n" +
            "　　萧源躺在柔软馨香的被褥里，隔着层层幔帐，隐约可见花罩外几名丫鬟仆妇正在给她熏衣，准备盥漱用具，" +
            "房里安静的连窗外的落雪声都能隐约听见。屋里火墙烧的正暖，萧源翻了一个身，掀了掀被窝散了些热气，“骨碌”一声，熏被的银香球被她无意间踢了下，滚到了地上。\n" +
            "\n" +
            "　　架子床外值夜的丫鬟灵偃正在望着窗户发呆，听到银香球落地的声音，打了一个激灵，" +
            "轻手轻脚的掀起一角床帘查看，见萧源已经睁开了眼睛，就轻声说：“夫人说这几天天气太冷，" +
            "姑娘们身子又弱，就不用这么早起身请安了，让你们多睡一会，姑架子床外值夜的丫鬟灵偃正在望着窗户发呆，" +
            "听到银香球落地的声音，打了一个激灵，轻手轻脚的掀起一角床帘查看，见萧源已经睁开了一夜，天还没亮，屋里漆黑一片，只有落地花罩外一盏小小的宫灯正发着微弱的黄光。\n" +
            "　　萧源躺在柔软馨香的被褥里，隔着层层幔帐，隐约可见花罩外几名丫鬟仆妇正在给她熏衣，准备盥漱用具，房里安静的连窗外的落雪声都能隐约听见。屋里火墙烧的正暖，萧源翻了一个身，掀了掀被窝散了些热气，“骨碌”一声，熏被的银香球被她无意间踢了下，滚到了地上。\n" +
            "\n" +
            "　　\n" +
            "\n" +
            "　　架子床外值夜的丫鬟灵偃正在望着窗户发呆，听到银香球落地的声音，打了一个激灵，轻手轻脚的掀起一角床帘查看，见萧源已经睁开了眼睛，就轻声说：“夫人说这几天天气太冷，姑娘们身子又弱，就不用这么早起身请安了，让你们多睡一会，姑架子床外值夜的丫鬟灵偃正在望着窗户发呆，听到银香球落地的声音，打了一个激灵，轻手轻脚的掀起一角床帘查看，见萧源已经睁开了眼睛，就轻声说：“夫人说这几天天气太冷，姑娘们身子又弱，就不用这么早起身请安了，让你们多睡一会，姑　　\n" +
            "\n" +
            "　　\n" +
            "\n" +
            "　　架子床外值夜的丫鬟灵偃正在望着窗户发呆，听到银香球落地的声音，打了一个激\n";
    String title = "第3章 最长30字章节名称最长30字章节名称最长30字章节名称";
    String author = "记得按时发几首歌罚款就是个示范岗后翻赶快来啦gals尬死了噶临时发发生\n"
            + "　　萧源躺在柔软馨香的被褥里，隔着层层幔帐，隐约可见花罩外几名丫鬟仆妇正在给她熏衣，准备盥漱用具，" +
            "房里安静的连窗外的落雪声都能隐约听见。屋里火墙烧的正暖，萧源翻了一个身，掀了掀被窝散了些热气，“骨碌”一声" +
            "，熏被的银香球被她无意间踢了下，滚到了地上。\n" + "　　萧源躺在柔软馨香的被褥里，隔着层层幔帐，隐约可见花罩外几名丫鬟仆妇正在给她熏衣，准备盥漱用具，" +
            "房里安静的连窗外的落雪声都能隐约听见。屋里火墙烧的正暖，萧源翻了一个身，掀了掀被窝散了些热气，“骨碌”一声" +
            "，熏被的银香球被她无意间踢了下，滚到了地上。\n" + "　　萧源躺在柔软馨香的被褥里，隔着层层幔帐，隐约可见花罩外几名丫鬟仆妇正在给她熏衣，准备盥漱用具，" +
            "房里安静的连窗外的落雪声都能隐约听见。屋里火墙烧的正暖，萧源翻了一个身，掀了掀被窝散了些热气，“骨碌”一声" +
            "，熏被的银香球被她无意间踢了下，滚到了地上。\n" + "　　萧源躺在柔软馨香的被褥里，隔着层层幔帐，隐约可见花罩外几名丫鬟仆妇正在给她熏衣，准备盥漱用具，" +
            "房里安静的连窗外的落雪声都能隐约听见。屋里火墙烧的正暖，萧源翻了一个身，掀了掀被窝散了些热气，“骨碌”一声" +
            "，熏被的银香球被她无意间踢了下，滚到了地上。\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ReaderView reader = findViewById(R.id.readview);

        reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick", "onClick: ");
                reader.setDraw(new RealMoveDraw(PageProperty.getInstance(MainActivity.this), reader));

                Toast.makeText(MainActivity.this, "出现菜单", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<ChaptersBean> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ChaptersBean bean = new ChaptersBean();
            bean.setId(i);
            bean.setAuthorContent(author);
            bean.setContent(word);
            bean.setName(title);
            list.add(bean);
        }
        MyReaderAdapter adapter = new MyReaderAdapter(this, list);
        reader.setReaderAdapter(adapter);
        reader.setDraw(new VerticalMoveDraw(PageProperty.getInstance(this), reader));
    }
}
