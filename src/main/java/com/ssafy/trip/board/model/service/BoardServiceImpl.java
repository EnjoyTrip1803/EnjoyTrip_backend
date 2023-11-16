package com.ssafy.trip.board.model.service;

import com.ssafy.trip.board.model.Board;
import com.ssafy.trip.board.model.FileInfo;
import com.ssafy.trip.board.model.dao.BoardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    @Autowired
    public BoardServiceImpl(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    @Transactional
    public void writeArticle(Board board) throws Exception {
        System.out.println("글입력 전 dto : " + board);
        boardDao.writeArticle(board);
        System.out.println("글입력 후 dto : " + board);
        List<FileInfo> fileInfos = board.getFileInfos();
        if (fileInfos != null && !fileInfos.isEmpty()) {
            boardDao.registerFile(board);
        }
    }

    @Override
    public List<Board> listArticle(Map<String, String> map) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        String key = map.get("key");
        if("userid".equals(key))
            key = "b.user_id";
        param.put("key", key == null ? "" : key);
        param.put("word", map.get("word") == null ? "" : map.get("word"));
        int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int start = pgNo * 10 - 10;
        param.put("start", start);
        param.put("listsize", 10);

        return boardDao.listArticle(param);
    }



    @Override
    public Board getArticle(int articleNo) throws Exception {
        return boardDao.getArticle(articleNo);
    }

    @Override
    public void updateHit(int articleNo) throws Exception {
        boardDao.updateHit(articleNo);
    }

    @Override
    public void modifyArticle(Board board) throws Exception {
        boardDao.modifyArticle(board);
    }

    @Override
    @Transactional
    public void deleteArticle(int articleNo, String path) throws Exception {
        List<FileInfo> fileList = boardDao.fileInfoList(articleNo);
        boardDao.deleteFile(articleNo);
        boardDao.deleteArticle(articleNo);
        for(FileInfo fileInfo : fileList) {
            File file = new File(path + File.separator + fileInfo.getSaveFolder() + File.separator + fileInfo.getSaveFile());
            file.delete();
        }
    }


}
