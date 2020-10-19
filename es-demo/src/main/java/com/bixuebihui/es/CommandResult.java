package com.bixuebihui.es;

import java.util.List;

/**
 * @author xwx
 */
public class CommandResult<T> {
    public static CommandResult<Void> ofSucceed() {
        return new CommandResult<Void>();
    }

    public static CommandResult<List<Notice>> ofSucceed(List<Notice> list) {
        return new CommandResult<>();
    }
}
