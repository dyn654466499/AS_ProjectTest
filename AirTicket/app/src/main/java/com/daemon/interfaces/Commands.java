package com.daemon.interfaces;

import android.os.Message;

/**
 * 命令模式接口
 * @author 邓耀宁
 *
 */
public interface Commands {
    /**
     * 封装参数
     * @param msg_params
     */
    public void executeCommand(Message msg_params);
}
