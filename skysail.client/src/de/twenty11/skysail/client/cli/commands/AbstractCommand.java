//package de.twenty11.skysail.client.cli.commands;
//
//import org.clamshellcli.api.Command;
//import org.clamshellcli.api.Context;
//
//public abstract class AbstractCommand implements Command {
//
//	protected Descriptor commandDescriptor = null;
//
//	public abstract Object doExecute(Context arg0);
//
//	@Override
//	public final Object execute(Context arg0) {
//		try {
//			return doExecute(arg0);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	@Override
//	public void plug(Context arg0) {
//	}
//
//	@Override
//	public Descriptor getDescriptor() {
//		return commandDescriptor;
//	}
//
//}
