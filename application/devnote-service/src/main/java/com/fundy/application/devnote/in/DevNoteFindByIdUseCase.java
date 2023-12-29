package com.fundy.application.devnote.in;

import com.fundy.application.devnote.in.dto.req.FindByIdRequest;
import com.fundy.application.devnote.in.dto.res.FindByIdResponse;

public interface DevNoteFindByIdUseCase {
    FindByIdResponse findById(final FindByIdRequest findByIdRequest);
}
