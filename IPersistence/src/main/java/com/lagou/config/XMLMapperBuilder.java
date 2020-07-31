package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        // 目前只支持 insert delete update select 4 种标签
        Iterator<Element> nodeIterator = rootElement.elementIterator();
        while (nodeIterator.hasNext()) {
            Element element = nodeIterator.next();
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            switch (element.getName()) {
                case "insert":
                case "delete":
                case "update":
                    mappedStatement.setSqlType("update");
                    mappedStatement.setResultType("java.lang.Integer");
                    break;
                case "select":
                    break;
                case "resultMap":
                    System.out.println("还不支持 resultMap 哦~");
                    break;
                default:
                    throw new NoSuchElementException("cannot recognize the element name : " + element.getName());
            }
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }


    }


}
