package com.fundy.application.devnote.in.dto.req;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindByIdRequest {
    private Long projectId;

}
