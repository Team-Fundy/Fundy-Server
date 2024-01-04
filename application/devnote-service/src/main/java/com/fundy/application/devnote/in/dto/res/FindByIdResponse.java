package com.fundy.application.devnote.in.dto.res;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FindByIdResponse {
    private String title;
    private String contents;
}
