package swm.s3.coclimb.docker;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class CommandProcessor {
    
    public boolean process(String command, String workingDir) {
        return process(command, workingDir, " ");
    }

    public boolean process(String command, String workingDir, String split) {
        return process(command.split(split), workingDir);
    }

    public boolean process(String[] command, String workingDir) {
        return process(command, workingDir, null);
    }

    public boolean process(String[] command, String workingDir, String writeString) {
        try {
            // 명령어를 실행할 프로세스를 생성합니다.
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File(workingDir));

            for (String s : command) {
                log.info("process command = {}", s);
            }

            // 프로세스를 실행합니다.
            Process process = processBuilder.start();
            if (writeString != null) {
                process.outputWriter().write(writeString);
            }

            // 프로세스의 출력을 읽어옵니다.
            readProcessOutput(process);

            // 프로세스가 완료될 때까지 기다립니다.
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                log.info("실행 완료");
                return true;
            } else {
                log.error("실행 실패");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void readProcessOutput(Process process) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            log.info(line);
        }
    }

}
