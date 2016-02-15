package com.containersol.minimesos.main;

import com.containersol.minimesos.cluster.MesosCluster;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MainTest {

    private ByteArrayOutputStream outputStream;
    private Main main;

    private CommandInfo commandInfo;
    private CommandDestroy commandDestroy;
    private CommandState commandState;
    private CommandInstall commandInstall;
    private CommandUp commandUp;

    @Before
    public void before() {

        outputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(outputStream, true);

        commandUp = spy(new CommandUp());
        doReturn(mock(MesosCluster.class)).when(commandUp).execute();

        commandDestroy = spy(new CommandDestroy());
        doNothing().when(commandDestroy).execute();

        commandInfo = spy(new CommandInfo(ps));
        doNothing().when(commandInfo).execute();

        commandState = spy(new CommandState(ps));
        doNothing().when(commandState).execute();

        commandInstall = spy(new CommandInstall());
        doNothing().when(commandInstall).execute();

        main = new Main();
        main.setOutput(ps);
        main.setCommandUp(commandUp);
        main.setCommandDestroy(commandDestroy);
        main.setCommandInfo(commandInfo);
        main.setCommandState(commandState);
        main.setCommandInstall(commandInstall);
        main.setCommandHelp(new CommandHelp());

    }


    @Test
    public void testUp() {
        main.run(new String[]{"up"});

        verify(commandUp).execute();
    }

    @Test
    public void testDestroy() {
        main.run(new String[]{"destroy"});

        verify(commandDestroy).execute();
    }

    @Test
    public void testInfo() throws IOException {
        main.run(new String[]{"info"});

        verify(commandInfo).execute();
    }

    @Test
    public void testState() throws IOException {
        main.run(new String[]{"state"});

        verify(commandState).execute();
    }

    @Test
    public void testInstall() throws IOException {
        main.run(new String[]{"install"});

        verify(commandInstall).execute();
    }

    @Test
    public void testUnsupportedCommand() throws IOException {
        main.run(new String[]{"unsupported"});
        String result = outputStream.toString();
        assertUsageText(result);
    }

    @Test
    public void testMinusMinusHelp() throws IOException {
        main.run(new String[]{"--help"});
        String result = outputStream.toString();
        assertUsageText(result);
    }

    @Test
    public void testHelp() throws IOException {
        main.run(new String[]{"help"});
        String result = outputStream.toString();
        assertUsageText(result);
    }

    private static void assertUsageText(String output) {
        assertTrue( output.contains("Usage: minimesos [options] [command] [command options]") );
        assertTrue( output.contains("Options:") );
        assertTrue( output.contains("Commands:") );
        assertTrue( output.contains("Usage: up [options]") );
        assertTrue( output.contains("Usage: install [options]") );
        assertTrue( output.contains("Usage: state [options]") );
    }

}