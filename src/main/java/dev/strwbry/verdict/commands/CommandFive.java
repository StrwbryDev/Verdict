package dev.strwbry.verdict.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;

/**
 * Command handler for pausing the tournament timer in the EventHorizon plugin.
 * Provides functionality to pause ongoing tournament events.
 * Requires operator permissions to execute.
 */
public class CommandFive
{

    /**
     * Builds the pause command structure with permission requirements.
     * Creates a command that can only be executed by operators.
     *
     * @param commandName The name of the command to be registered
     * @return LiteralCommandNode containing the configured command structure
     */
    public static LiteralCommandNode<CommandSourceStack> buildCommand(final String commandName) {
        return Commands.literal(commandName)
                .requires(sender -> sender.getSender().isOp())
                .executes(CommandFive::executeCommandLogic)
                .build();
    }

    /**
     * Executes the pause command logic by attempting to pause the tournament timer.
     * Sends feedback message to the command sender indicating success or failure.
     *
     * @param ctx Command context containing the sender information
     * @return Command success status
     */
    private static int executeCommandLogic(CommandContext<CommandSourceStack> ctx){
        CommandSender sender = ctx.getSource().getSender(); // Retrieve the command sender

        //Execute command logic here

        return Command.SINGLE_SUCCESS;
    }
}
