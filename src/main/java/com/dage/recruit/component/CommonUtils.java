package com.dage.recruit.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class CommonUtils {
    public Map<String, String> returnMapData(Map<String,String[]> map){

        Iterator it = map.keySet().iterator();

        String keys = null;
        String[] value = null;

        Map<String,String> returnMap = new HashMap<>();

        while(it.hasNext()){
            keys = String.valueOf(it.next());
            value = map.get(keys);
            for (int i = 0 ; i < value.length ; i++){
                returnMap.put(keys,value[i]);
            }
        }
        return returnMap;
    }

    public Map<String, Object> returnMapDataObj(Map<String,String[]> map){

        Iterator it = map.keySet().iterator();

        String keys = null;
        String[] value = null;

        Map<String,Object> returnMap = new HashMap<>();

        while(it.hasNext()){
            keys = String.valueOf(it.next());
            value = map.get(keys);
            for (int i = 0 ; i < value.length ; i++){
                returnMap.put(keys,value[i]);
            }
        }
        return returnMap;
    }

    public void setDefaultValues(Map<String, Object> map, String mapName) {
        // 기본값 설정
        Map<String, String> basicInfo = new HashMap<>();

        basicInfo.put("sex_tag", "");
        basicInfo.put("user_tel", "");
        basicInfo.put("front", "");
        basicInfo.put("middle", "");
        basicInfo.put("back", "");
        basicInfo.put("post_num_h", "");
        basicInfo.put("address_h", "");
        basicInfo.put("address_h2", "");
        basicInfo.put("welfare_tag", "");
        basicInfo.put("obstacle_tag", "");
        basicInfo.put("mil_srvc", "");
        basicInfo.put("mil_type", "");
        basicInfo.put("mil_type_rank", "");
        basicInfo.put("mil_discharge", "");
        basicInfo.put("sdt_y", "");
        basicInfo.put("sdt_m", "");
        basicInfo.put("edt_y", "");
        basicInfo.put("edt_m", "");
        basicInfo.put("mil_exem", "");

        // 기본값 설정
        Map<String, String> education = new HashMap<>();

        education.put("high_sdt_y", "");
        education.put("high_sdt_m", "");
        education.put("high_edt_y", "");
        education.put("high_edt_m", "");
        education.put("high_grd", "");
        education.put("high_name", "");
        education.put("high_fn", "False");
        education.put("high_ser", "");
        education.put("col_sdt_y", "");
        education.put("col_sdt_m", "");
        education.put("col_edt_y", "");
        education.put("col_edt_m", "");
        education.put("college_grd", "");
        education.put("college_name", "");
        education.put("college_loc", "");
        education.put("college_dn", "");
        education.put("h209", "주전공");
        education.put("college_cls", "");
        education.put("college_cls_sub", "");
        education.put("college_cls_sub_name", "");
        education.put("college_crd", "");
        education.put("college_crd_max", "");
        education.put("univ_sdt_y", "");
        education.put("univ_sdt_m", "");
        education.put("univ_edt_y", "");
        education.put("univ_edt_m", "");
        education.put("univ_grd", "");
        education.put("univ_name", "");
        education.put("univ_loc", "");
        education.put("univ_dn", "");
        education.put("h309", "주전공");
        education.put("univ_cls", "");
        education.put("univ_cls_sub", "");
        education.put("univ_cls_sub_name", "");
        education.put("univ_crd", "");
        education.put("univ_crd_max", "");
        education.put("univ_sdt_y_2", "");
        education.put("univ_sdt_m_2", "");
        education.put("univ_edt_y_2", "");
        education.put("univ_edt_m_2", "");
        education.put("univ_grd_2", "");
        education.put("univ_name_2", "");
        education.put("univ_loc_2", "");
        education.put("univ_dn_2", "");
        education.put("h409", "주전공");
        education.put("univ_cls_2", "");
        education.put("univ_cls_sub_2", "");
        education.put("univ_cls_sub_name_2", "");
        education.put("univ_crd_2", "");
        education.put("univ_crd_max_2", "");
        education.put("grad_sdt_y", "");
        education.put("grad_sdt_m", "");
        education.put("grad_edt_y", "");
        education.put("grad_edt_m", "");
        education.put("grad_grd", "");
        education.put("grad_name", "");
        education.put("grad_loc", "");
        education.put("grad_dn", "");
        education.put("h509", "주전공");
        education.put("grad_cls", "");
        education.put("grad_cls_sub", "");
        education.put("grad_cls_sub_name", "");
        education.put("grad_crd", "");
        education.put("grad_crd_max", "");
        education.put("grad_thesis", "");
        education.put("grad_sdt_y_2", "");
        education.put("grad_sdt_m_2", "");
        education.put("grad_edt_y_2", "");
        education.put("grad_edt_m_2", "");
        education.put("grad_grd_2", "");
        education.put("grad_name_2", "");
        education.put("grad_loc_2", "");
        education.put("grad_dn_2", "");
        education.put("h609", "주전공");
        education.put("grad_cls_2", "");
        education.put("grad_cls_sub_2", "");
        education.put("grad_cls_sub_name_2", "");
        education.put("grad_crd_2", "");
        education.put("grad_crd_max_2", "");
        education.put("grad_thesis_2", "");

        Map<String, String> experience = new HashMap<>();
        experience.put("co_sdt_y_1", "");
        experience.put("co_sdt_m_1", "");
        experience.put("co_edt_y_1", "");
        experience.put("co_edt_m_1", "");
        experience.put("co_month_1", "");
        experience.put("co_name_1", "");
        experience.put("co_dept_1", "");
        experience.put("co_rank_1", "");
        experience.put("co_salary_1", "");
        experience.put("co_work_1", "");
        experience.put("co_retire_1", "");
        experience.put("co_sdt_y_2", "");
        experience.put("co_sdt_m_2", "");
        experience.put("co_edt_y_2", "");
        experience.put("co_edt_m_2", "");
        experience.put("co_month_2", "");
        experience.put("co_name_2", "");
        experience.put("co_dept_2", "");
        experience.put("co_rank_2", "");
        experience.put("co_salary_2", "");
        experience.put("co_work_2", "");
        experience.put("co_retire_2", "");
        experience.put("co_sdt_y_3", "");
        experience.put("co_sdt_m_3", "");
        experience.put("co_edt_y_3", "");
        experience.put("co_edt_m_3", "");
        experience.put("co_month_3", "");
        experience.put("co_name_3", "");
        experience.put("co_dept_3", "");
        experience.put("co_rank_3", "");
        experience.put("co_salary_3", "");
        experience.put("co_work_3", "");
        experience.put("co_retire_3", "");
        experience.put("co_sdt_y_4", "");
        experience.put("co_sdt_m_4", "");
        experience.put("co_edt_y_4", "");
        experience.put("co_edt_m_4", "");
        experience.put("co_month_4", "");
        experience.put("co_name_4", "");
        experience.put("co_dept_4", "");
        experience.put("co_rank_4", "");
        experience.put("co_salary_4", "");
        experience.put("co_work_4", "");
        experience.put("co_retire_4", "");
        experience.put("co_sdt_y_5", "");
        experience.put("co_sdt_m_5", "");
        experience.put("co_edt_y_5", "");
        experience.put("co_edt_m_5", "");
        experience.put("co_month_5", "");
        experience.put("co_name_5", "");
        experience.put("co_dept_5", "");
        experience.put("co_rank_5", "");
        experience.put("co_salary_5", "");
        experience.put("co_work_5", "");
        experience.put("co_retire_5", "");

        Map<String, String> language = new HashMap<>();
        language.put("lang_1", "");
        language.put("lang_test_1", "");
        language.put("lang_score_1", "");
        language.put("lang_lv_1", "");
        language.put("lang_dt_y_1", "");
        language.put("lang_dt_m_1", "");
        language.put("lang_dt_d_1", "");
        language.put("lang_2", "");
        language.put("lang_test_2", "");
        language.put("lang_score_2", "");
        language.put("lang_lv_2", "");
        language.put("lang_dt_y_2", "");
        language.put("lang_dt_m_2", "");
        language.put("lang_dt_d_2", "");
        language.put("lang_3", "");
        language.put("lang_test_3", "");
        language.put("lang_score_3", "");
        language.put("lang_lv_3", "");
        language.put("lang_dt_y_3", "");
        language.put("lang_dt_m_3", "");
        language.put("lang_dt_d_3", "");
        language.put("lang_4", "");
        language.put("lang_test_4", "");
        language.put("lang_score_4", "");
        language.put("lang_lv_4", "");
        language.put("lang_dt_y_4", "");
        language.put("lang_dt_m_4", "");
        language.put("lang_dt_d_4", "");
        language.put("lang_5", "");
        language.put("lang_test_5", "");
        language.put("lang_score_5", "");
        language.put("lang_lv_5", "");
        language.put("lang_dt_y_5", "");
        language.put("lang_dt_m_5", "");
        language.put("lang_dt_d_5", "");

        Map<String, String> cert = new HashMap<>();
        cert.put("cert_cls_1", "");
        cert.put("cert_dt_y_1", "");
        cert.put("cert_dt_m_1", "");
        cert.put("cert_dt_d_1", "");
        cert.put("cert_isu_1", "");
        cert.put("cert_reg_num_1", "");
        cert.put("cert_cls_2", "");
        cert.put("cert_dt_y_2", "");
        cert.put("cert_dt_m_2", "");
        cert.put("cert_dt_d_2", "");
        cert.put("cert_isu_2", "");
        cert.put("cert_reg_num_2", "");
        cert.put("cert_cls_3", "");
        cert.put("cert_dt_y_3", "");
        cert.put("cert_dt_m_3", "");
        cert.put("cert_dt_d_3", "");
        cert.put("cert_isu_3", "");
        cert.put("cert_reg_num_3", "");
        cert.put("cert_cls_4", "");
        cert.put("cert_dt_y_4", "");
        cert.put("cert_dt_m_4", "");
        cert.put("cert_dt_d_4", "");
        cert.put("cert_isu_4", "");
        cert.put("cert_reg_num_4", "");
        cert.put("cert_word", "");
        cert.put("cert_excel", "");
        cert.put("cert_ppt", "");
        cert.put("cert_cad", "");
        cert.put("cert_etc_name", "");
        cert.put("cert_etc", "");

        // 전달된 맵에 따라 해당하는 기본값 설정
        if (mapName.equals("basicInfo")) {
            setDefaultValuesForMap(map, basicInfo);
        } else if (mapName.equals("education")) {
            setDefaultValuesForMap(map, education);
        } else if (mapName.equals("experience")) {
            setDefaultValuesForMap(map, experience);
        } else if (mapName.equals("language")) {
            setDefaultValuesForMap(map, language);
        } else if (mapName.equals("certifications")) {
            setDefaultValuesForMap(map, cert);
        }
    }

    /* 날짜 분리 */
    public void processDate(Map<String, Object> map, String startDateKey, String endDateKey, String startYearKey, String startMonthKey, String endYearKey, String endMonthKey) {
        String startDate = (String) map.get(startDateKey);
        String endDate = (String) map.get(endDateKey);

        if ((startDate != null && !startDate.equals("")) && (endDate != null && !endDate.equals(""))) {
            String[] startDateParts = startDate.split("-");
            String[] endDateParts = endDate.split("-");

            if (startDateParts.length == 2) {
                map.put(startYearKey, startDateParts[0]);
                map.put(startMonthKey, startDateParts[1]);
                map.put(endYearKey, endDateParts[0]);
                map.put(endMonthKey, endDateParts[1]);
            }
        }
    }

    public void splitDate(Map<String, Object> map, String DateKey, String year, String month, String day) {
        String dateKey = (String) map.get(DateKey);

        if (dateKey != null && !dateKey.equals("")) {
            String[] dateParts = dateKey.split("-");
            if (dateParts.length == 3) {
                map.put(year, dateParts[0]);
                map.put(month, dateParts[1]);
                map.put(day, dateParts[2]);
            }
        }
    }

    private void setDefaultValuesForMap(Map<String, Object> map, Map<String, String> defaultValues) {
        for (String field : defaultValues.keySet()) {
            if (!map.containsKey(field) || map.get(field) == null || map.get(field).toString().isEmpty()) {
                map.put(field, defaultValues.get(field));
            }
        }
    }

}
