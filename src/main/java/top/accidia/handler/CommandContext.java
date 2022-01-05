package top.accidia.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import top.accidia.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author accidia
 */
public class CommandContext {
    private static final Map<String, CommandHandler> COMMAND_HANDLERS;

    static {
        COMMAND_HANDLERS = new HashMap<>();
        // 所有命令都存放在 top.accidia.handler 这个路径里
        Set<Class<?>> classes = ClassUtil.scanPackage("top.accidia.handler");
        for (Class<?> aClass : classes) {
            boolean contains = CollectionUtil.toList(aClass.getInterfaces()).contains(CommandHandler.class);
            if (contains) {
                CommandHandler commandHandler = ReflectUtil.newInstance(aClass.getName());
                COMMAND_HANDLERS.put(commandHandler.getCommand().getName(), commandHandler);
            }
        }
    }

    public static CommandHandler getMessageHandler(String content) {
        String[] commands = MessageUtil.splitCommands(content);
        return COMMAND_HANDLERS.get(commands[0]);
    }
}
