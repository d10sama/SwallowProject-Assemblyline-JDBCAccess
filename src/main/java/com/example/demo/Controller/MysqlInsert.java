package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;

public class MysqlInsert {

    private final String ins=
            "insert into javabook.product_table\n" +
                    "(\n" +
                    "line_product_Id,\n" +
                    "online_time_year,\n" +
                    "online_time_month,\n" +
                    "online_time_day,\n" +
                    "online_time_hour,\n" +
                    "online_time_minute,\n" +
                    "online_time_second,\n" +
                    "offline_time_year,\n" +
                    "offline_time_month,\n" +
                    "offline_time_day,\n" +
                    "offline_time_hour,\n" +
                    "offline_time_minute,\n" +
                    "offline_time_second,\n" +
                    "time_difference,\n" +
                    "line1_station2_product_time,\n" +
                    "line1_station3_product_time,\n" +
                    "line1_station4_product_time,\n" +
                    "line1_station5_product_time,\n" +
                    "line1_station7_product_time,\n" +
                    "line1_station8_product_time,\n" +
                    "line1_station10_product_time,\n" +
                    "product_total_time,\n" +
                    "line1_station2_product_bypass,\n" +
                    "line1_station3_product_bypass,\n" +
                    "line1_station4_product_bypass,\n" +
                    "line1_station5_product_bypass,\n" +
                    "line1_station6_product_bypass,\n" +
                    "line1_station7_product_bypass,\n" +
                    "line1_station8_product_bypass,\n" +
                    "line1_station9_product_bypass,\n" +
                    "line1_station10_product_bypass,\n" +
                    "line1_station2_product_OK,\n" +
                    "line1_station3_product_OK,\n" +
                    "line1_station4_product_OK,\n" +
                    "line1_station5_product_OK,\n" +
                    "line1_station6_product_OK,\n" +
                    "line1_station7_product_OK,\n" +
                    "line1_station8_product_OK,\n" +
                    "line1_station9_product_OK,\n" +
                    "line1_station10_product_OK,\n" +
                    "product_total_OK,\n" +
                    "line1_station2_product_NG,\n" +
                    "line1_station3_product_NG,\n" +
                    "line1_station4_product_NG,\n" +
                    "line1_station5_product_NG,\n" +
                    "line1_station6_product_NG,\n" +
                    "line1_station7_product_NG,\n" +
                    "line1_station8_product_NG,\n" +
                    "line1_station9_product_NG,\n" +
                    "line1_station10_product_NG,\n" +
                    "spc_line1_station010_pressdata_1,\n" +
                    "spc_line1_station010_displacementdata_1,\n" +
                    "spc_line1_station010_pressdata_2,\n" +
                    "spc_line1_station010_displacementdata_2,\n" +
                    "spc_line1_station010_pressdata_3,\n" +
                    "spc_line1_station010_displacementdata_3,\n" +
                    "spc_line1_station010_pressdata_4,\n" +
                    "spc_line1_station010_displacementdata_4,\n" +
                    "spc_line1_station020_pressdata_1,\n" +
                    "spc_line1_station020_displacementdata_1,\n" +
                    "spc_line1_station020_pressdata_2,\n" +
                    "spc_line1_station020_displacementdata_2,\n" +
                    "spc_line1_station020_pressdata_3,\n" +
                    "spc_line1_station020_displacementdata_3,\n" +
                    "spc_line1_station020_pressdata_4,\n" +
                    "spc_line1_station020_displacementdata_4,\n" +
                    "spc_line1_station040_gunNumber_1,\n" +
                    "spc_line1_station040_torque_1,\n" +
                    "spc_line1_station040_angle_1,\n" +
                    "spc_line1_station040_result_1,\n" +
                    "spc_line1_station040_gunNumber_2,\n" +
                    "spc_line1_station040_torque_2,\n" +
                    "spc_line1_station040_angle_2,\n" +
                    "spc_line1_station040_result_2,\n" +
                    "spc_line1_station040_gunNumber_3,\n" +
                    "spc_line1_station040_torque_3,\n" +
                    "spc_line1_station040_angle_3,\n" +
                    "spc_line1_station040_result_3,\n" +
                    "spc_line1_station040_gunNumber_4,\n" +
                    "spc_line1_station040_torque_4,\n" +
                    "spc_line1_station040_angle_4,\n" +
                    "spc_line1_station040_result_4,\n" +
                    "spc_line1_station040_gunNumber_5,\n" +
                    "spc_line1_station040_torque_5,\n" +
                    "spc_line1_station040_angle_5,\n" +
                    "spc_line1_station040_result_5,\n" +
                    "spc_line1_station040_gunNumber_6,\n" +
                    "spc_line1_station040_torque_6,\n" +
                    "spc_line1_station040_angle_6,\n" +
                    "spc_line1_station040_result_6,\n" +
                    "spc_line1_station040_gunNumber_7,\n" +
                    "spc_line1_station040_torque_7,\n" +
                    "spc_line1_station040_angle_7,\n" +
                    "spc_line1_station040_result_7,\n" +
                    "spc_line1_station040_gunNumber_8,\n" +
                    "spc_line1_station040_torque_8,\n" +
                    "spc_line1_station040_angle_8,\n" +
                    "spc_line1_station040_result_8,\n" +
                    "spc_line1_station040_gunNumber_9,\n" +
                    "spc_line1_station040_torque_9,\n" +
                    "spc_line1_station040_angle_9,\n" +
                    "spc_line1_station040_result_9,\n" +
                    "spc_line1_station040_gunNumber_10,\n" +
                    "spc_line1_station040_torque_10,\n" +
                    "spc_line1_station040_angle_10,\n" +
                    "spc_line1_station040_result_10,\n" +
                    "spc_line1_station070_gunNumber_1,\n" +
                    "spc_line1_station070_torque_1,\n" +
                    "spc_line1_station070_angle_1,\n" +
                    "spc_line1_station070_result_1,\n" +
                    "spc_line1_station070_gunNumber_2,\n" +
                    "spc_line1_station070_torque_2,\n" +
                    "spc_line1_station070_angle_2,\n" +
                    "spc_line1_station070_result_2,\n" +
                    "spc_line1_station070_gunNumber_3,\n" +
                    "spc_line1_station070_torque_3,\n" +
                    "spc_line1_station070_angle_3,\n" +
                    "spc_line1_station070_result_3,\n" +
                    "spc_line1_station070_gunNumber_4,\n" +
                    "spc_line1_station070_torque_4,\n" +
                    "spc_line1_station070_angle_4,\n" +
                    "spc_line1_station070_result_4,\n" +
                    "spc_line1_station070_gunNumber_5,\n" +
                    "spc_line1_station070_torque_5,\n" +
                    "spc_line1_station070_angle_5,\n" +
                    "spc_line1_station070_result_5,\n" +
                    "spc_line1_station070_gunNumber_6,\n" +
                    "spc_line1_station070_torque_6,\n" +
                    "spc_line1_station070_angle_6,\n" +
                    "spc_line1_station070_result_6,\n" +
                    "spc_line1_station070_gunNumber_7,\n" +
                    "spc_line1_station070_torque_7,\n" +
                    "spc_line1_station070_angle_7,\n" +
                    "spc_line1_station070_result_7,\n" +
                    "spc_line1_station070_gunNumber_8,\n" +
                    "spc_line1_station070_torque_8,\n" +
                    "spc_line1_station070_angle_8,\n" +
                    "spc_line1_station070_result_8,\n" +
                    "spc_line1_station070_gunNumber_9,\n" +
                    "spc_line1_station070_torque_9,\n" +
                    "spc_line1_station070_angle_9,\n" +
                    "spc_line1_station070_result_9,\n" +
                    "spc_line1_station070_gunNumber_10,\n" +
                    "spc_line1_station070_torque_10,\n" +
                    "spc_line1_station070_angle_10,\n" +
                    "spc_line1_station070_result_10)\n" +
                    " values\n" +
                    "(\"XXXXX-001-XXXXXXXX\",\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "1,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000,\n" +
                    "rand()*1000);";


    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;
    @RequestMapping("/ins")
    void MysqlInsert()
    {
        jdbcTemplate1.execute(ins);
    }
}