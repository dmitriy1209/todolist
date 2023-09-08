package com.digital.uwp.model.dto;

import java.io.Serializable;

public interface BaseDto<E> extends Serializable {

    E fromDto();
}
