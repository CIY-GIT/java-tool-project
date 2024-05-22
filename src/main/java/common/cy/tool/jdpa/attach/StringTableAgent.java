package common.cy.tool.jdpa.attach;

import sun.jvm.hotspot.memory.StringTable;
import sun.jvm.hotspot.memory.SystemDictionary;
import sun.jvm.hotspot.oops.Instance;
import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.oops.OopField;
import sun.jvm.hotspot.oops.TypeArray;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.tools.Tool;
/**
 * @Title: StringTableAttach
 * @Package common.cy.tool.jdpa.attach
 * @Description:
 * @author hzchenya
 * @date 2019-12-09 10:04
 * @version TODO
 */
public class StringTableAgent extends Tool
{
	public StringTableAgent()
	{
	}

	public static void main(String args[]) throws Exception
	{
		if (args.length == 0 || args.length > 1)
		{
			System.err.println("Usage: java StringTableAgent <PID of the JVM whose string table you want to print>");
			System.exit(1);
		}
		StringTableAgent pst = new StringTableAgent();
		pst.execute(args);
		pst.stop();
	}

	@Override
	public void run()
	{
		StringTable table = VM.getVM().getStringTable();
		table.stringsDo(new StringPrinter());
	}

	class StringPrinter implements StringTable.StringVisitor
	{
		private final OopField stringValueField;

		public StringPrinter()
		{
			InstanceKlass strKlass = SystemDictionary.getStringKlass();
			stringValueField = (OopField) strKlass.findField("value", "[C");
		}

		@Override
		public void visit(Instance instance)
		{
			TypeArray charArray = ((TypeArray) stringValueField.getValue(instance));
			StringBuilder sb = new StringBuilder();
			for (long i = 0; i < charArray.getLength(); i++)
			{
				sb.append(charArray.getCharAt(i));
			}
			System.out.println("Address: " + instance.getHandle() + " Content: " + sb.toString());
		}
	}
}