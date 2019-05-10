package com.controller.level;

import com.dao.PersonLevelScoreDao;
import com.dao.QualityScoreRangeDao;
import com.entity.PersonLevelScore;
import com.entity.QualityScoreRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/pages/image")
public class ImageReachSetController {

    @Autowired
    private QualityScoreRangeDao qualityScoreRangeDao;
    @Autowired
    private PersonLevelScoreDao personLevelScoreDao;

    @RequestMapping(value = "/levelScore",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> LevelScoreSet(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,int[]> scoreMap = new HashMap<>();
        try{
            int yx_min = Integer.parseInt(request.getParameter("yx_min"));
            int yx_max = Integer.parseInt(request.getParameter("yx_max"));
            scoreMap.put("1",new int[]{yx_min,yx_max});
            int lh_min = Integer.parseInt(request.getParameter("lh_min"));
            int lh_max = Integer.parseInt(request.getParameter("lh_max"));
            scoreMap.put("2",new int[]{lh_min,lh_max});
            int yb_min = Integer.parseInt(request.getParameter("yb_min"));
            int yb_max = Integer.parseInt(request.getParameter("yb_max"));
            scoreMap.put("3",new int[]{yb_min,yb_max});
            int jc_min = Integer.parseInt(request.getParameter("jc_min"));
            int jc_max = Integer.parseInt(request.getParameter("jc_max"));
            scoreMap.put("4",new int[]{jc_min,jc_max});
            int hc_min = Integer.parseInt(request.getParameter("hc_min"));
            int hc_max = Integer.parseInt(request.getParameter("hc_max"));
            scoreMap.put("5",new int[]{hc_min,hc_max});

            for(int i = 1;i<6;i++){
                QualityScoreRange qualityScoreRange = qualityScoreRangeDao.findByLevel(i);
                if(null == qualityScoreRange) qualityScoreRange = new QualityScoreRange();
                qualityScoreRange.setLevel(i);
                qualityScoreRange.setMinScore(scoreMap.get(String.valueOf(i))[0]);
                qualityScoreRange.setMaxScore(scoreMap.get(String.valueOf(i))[1]);
                qualityScoreRange.setInputTime(new Date());

                String name = null;
                switch(i) {
                    case 1:
                        name = "优秀";
                        break;
                    case 2:
                        name = "良好";
                        break;
                    case 3:
                        name = "一般";
                        break;
                    case 4:
                        name = "较差";
                        break;
                    case 5:
                        name = "很差";
                        break;
                }
                qualityScoreRange.setName(name);
                qualityScoreRangeDao.save(qualityScoreRange);
            }
            resultMap.put("success",true);
            resultMap.put("message","ok");
        }catch(Exception ex){
            resultMap.put("success",false);
            resultMap.put("message",ex.getMessage());
            ex.printStackTrace();
        }

        return resultMap;
    }

    @RequestMapping(value ="/fingerLevel",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>  fingerLevelSet(@RequestParam("value_a[]") int[] array_a,
                                              @RequestParam("value_b[]") int[] array_b,
                                              @RequestParam("value_c[]") int[] array_c){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,int[]> levelMap = new HashMap<>();
        try{
            levelMap.put("A",array_a);
            levelMap.put("B",array_b);
            levelMap.put("C",array_c);
            for(Map.Entry<String,int[]> map : levelMap.entrySet()){
                PersonLevelScore personLevelScore = personLevelScoreDao.findByLevel(map.getKey());
                if(personLevelScore == null) personLevelScore = new PersonLevelScore();
                personLevelScore.setLevel(map.getKey());
                personLevelScore.setRm(map.getValue()[0]);
                personLevelScore.setRs(map.getValue()[1]);
                personLevelScore.setRz(map.getValue()[2]);
                personLevelScore.setRh(map.getValue()[3]);
                personLevelScore.setRx(map.getValue()[4]);
                personLevelScore.setLm(map.getValue()[5]);
                personLevelScore.setLs(map.getValue()[6]);
                personLevelScore.setLz(map.getValue()[7]);
                personLevelScore.setLh(map.getValue()[8]);
                personLevelScore.setLx(map.getValue()[9]);
                personLevelScore.setInputTime(new Date());
                personLevelScoreDao.save(personLevelScore);

            }

            resultMap.put("success",true);
            resultMap.put("message","ok");
        }catch(Exception ex){
            resultMap.put("success",false);
            resultMap.put("message",ex.getMessage());
            ex.printStackTrace();
        }

        return resultMap;
    }
}