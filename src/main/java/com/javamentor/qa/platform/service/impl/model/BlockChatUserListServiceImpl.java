package com.javamentor.qa.platform.service.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.BlockChatUserListDao;
import com.javamentor.qa.platform.models.entity.user.BlockChatUserList;
import com.javamentor.qa.platform.service.abstracts.model.BlockChatUserListService;
import org.springframework.stereotype.Service;

@Service
public class BlockChatUserListServiceImpl extends ReadWriteServiceImpl<BlockChatUserList, Long> implements BlockChatUserListService {
    public BlockChatUserListServiceImpl(BlockChatUserListDao blockChatUserListDao) {
        super(blockChatUserListDao);
    }
}
