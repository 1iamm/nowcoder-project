package com.nowcoder.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.tree.TreeNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveFilter.class);

    //    替换符号
    private static final String REPLACEMENT = "***";

    private TrieNode rootNode = new TrieNode();

    @PostConstruct  //表示当容器实例化SensitiveFilter调用构造器之后就会自动调用该方法
    public void init() {
        try (
//                字符流 -> 字节流 -> 缓冲流
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");  //获取类加载器，从类路径（classes路径）下加载资源，程序编译后所有程序都会编译到classes路径下
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
//                把敏感词添加到前缀树对象
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            LOGGER.error("加载铭感词文件失败： " + e.getMessage());
        }
    }

    //    将一个敏感词添加到前缀树中
    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if (subNode == null) {
//                初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            tempNode = subNode;

//            设置结束标识
            if (i == keyword.length() - 1)
                tempNode.setKeywordEnd(true);
        }
    }

    /**
     * 被外界调用的过滤敏感词方法，返回的是String
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text))
            return null;
//        开始过滤，声明三个指针
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
//        返回的结果
        StringBuilder stringBuilder = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

//            跳过符号
            if (isSymbol(c)) {
//                若指针1为根节点,将此符号计入结果，让指针2向下走一步
                if (tempNode == rootNode) {
                    stringBuilder.append(c);
                    begin++;
                }
//                无论符号在开头或中间，指针3都需要向下走一步
                position++;
                continue;
            }

//            开始检测下级节点
            tempNode = tempNode.getSubNode(c);
//            以Begin开头的字符不是敏感词
            if (tempNode == null) {
                stringBuilder.append(text.charAt(begin));
                tempNode = rootNode;
                begin++;
                position = begin;
            }else if (tempNode.isKeywordEnd) {
//                发现敏感词，将begin~position字符串替换掉
                stringBuilder.append(REPLACEMENT);
                position++;
                begin = position;
            }else {
//                在检测途中,继续检测下一个字符
                position++;
            }

        }
//        将最后一批字符计入结果
        stringBuilder.append(text.substring(begin));

        return stringBuilder.toString();
    }


    //    判断是否为符号
    private boolean isSymbol(Character c) {
//        0x2E80 ~ 0x9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    //    前缀树
    private class TrieNode {
        //        描述关键词结束标识
        private boolean isKeywordEnd = false;

        //        当前节点的子节点，key是下级字符，value是下级节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        //        添加子节点
        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);
        }

        //        获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
    }


}
