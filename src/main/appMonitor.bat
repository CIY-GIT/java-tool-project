@Echo off
cd E:\workspace\workspace_ntes\project-tool\out\artifacts\appMonitor
java -DftpPath=E:\线上GC及Dump\32 -DstartSh=startUp.bat -DstopSh=shutdoown.bat -DrestartNum=50 -Dheartbeat=5 -jar appMonitor.jar > appMonitor.log
@pause