package com.ducetech.app.model.vo;

import com.ducetech.app.model.Fault;
import lombok.Data;

import java.util.List;

/**
 * Created by BLUE on 2017/7/11.
 */
@Data
public class FaultVo extends Fault {
    private List<Fault> faults;
}
