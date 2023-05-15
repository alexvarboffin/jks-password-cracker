package org.walhalla;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainController {

    Configuration cfg;

    public MainController() {
        // Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.29) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
        cfg = new Configuration(Configuration.VERSION_2_3_29);

        try {
            cfg.setDirectoryForTemplateLoading(new File("D:\\src\\Inflatter\\Input\\templates"));
            cfg.setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
            cfg.setLogTemplateExceptions(false);

// Wrap unchecked exceptions thrown during template processing into TemplateException-s:
            cfg.setWrapUncheckedExceptions(true);

            // Do not fall back to higher scopes when reading a null loop variable:
            cfg.setFallbackOnNullLoopVariable(false);

        } catch (Exception y) {
            y.printStackTrace();
        }
    }

    public void buildFromJsonClassList(String json) {

        //Read POJO Object

        JSONParser parser = new JSONParser();
        Object o = null;
        try {
            o = parser.parse(new FileReader(json));
        } catch (ParseException | FileNotFoundException parseException) {
            parseException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        JSONObject object0 = null;
        List<ValueFieldObject> ccFields0 = new ArrayList<>();
        List<ValueClassObject> classes0 = new ArrayList<>();

        if (o instanceof JSONObject) {
            object0 = (JSONObject) o; // your json object
            for (Object key : object0.keySet()) {
                //log(key + "=" + object.get(key)); // to get the value
                ccFields0.add(new ValueFieldObject(key.toString(), object0.get("" + key).getClass()));
            }
        } else if (o instanceof JSONArray) {
            JSONArray array = (JSONArray) o;
            for (Object key : array) {


                JSONObject object = (JSONObject) key;


                for (Object o1 : object.keySet()) {

                    String name = o1.toString();
                    List<ValueFieldObject> fieldObjects = new ArrayList<>();

                    JSONObject obj = (JSONObject) object.get(name); // your json object
                    for (Object key88 : obj.keySet()) {
                        //log(key + "=" + object.get(key)); // to get the value

                        fieldObjects.add(new ValueFieldObject(key88.toString(), obj.get("" + key88).getClass()));
                    }
                    ValueClassObject cc0 = new ValueClassObject(
                            o1.toString(),
                            o1.toString().toLowerCase(),
                            //privateFields0
                            fieldObjects
                    );
                    classes0.add(cc0);
                    //log(o1 + "=" + object.get(o1)); // to get the value
                    ccFields0.add(new ValueFieldObject(o1.toString(), object.get("" + o1).getClass()));
                }
            }
        }

        //Generate Class Name from path_url
        String[] tmp = json.split("\\\\");
        String single_class = tmp[tmp.length - 1].replace(".json", "");

        ValueClassObject cc = new ValueClassObject(
                single_class,
                single_class.toLowerCase(),
                //privateFields0
                ccFields0
        );


        HashMap<String, Object> data = new HashMap<>();
        data.put("class", cc);//<-- One obj
        data.put("classes", classes0);//<-- All Objects
        data.put("fields", ccFields0);//privateFields
        data.put("package", "99999999.9999999");
        //data.put("classes", "kkkkkkkkkkk");
        //input.put("fields", privateFields);


        //genereteData(repository.getOptions(), _input, c);//? cc

        List<BaseValues> arr = new ArrayList<>();
        arr.add(cc);


        //StringTemplateLoader templateLoader = new StringTemplateLoader();
        //templateLoader.putTemplate("sysTemplate", "mmmmmm" + __t);
        //templateLoader.putTemplate("myTemplate", "<#include \"greetTemplate\"><@greet/> World!");

        String result;
        try {
            Template template = cfg.getTemplate("abc.ftl");
            //configuration.setTemplateLoader(templateLoader);
            //Template template = configuration.getTemplate("sysTemplate");
            StringWriter out = new StringWriter();
            template.process(data, out);
            result = out.toString();
            out.close();
            appendLog("\n======================\n" + result);

        } catch (Exception eo) {
            appendLog(eo.getLocalizedMessage());
        }
    }

    private void appendLog(String o) {
        System.out.println(o);
    }
}
