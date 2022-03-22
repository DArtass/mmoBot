Option Explicit

Dim Shell, WMI, wql, process

Set Shell = CreateObject("WScript.Shell")
Set WMI = GetObject("winmgmts: {impersonationLevel=impersonate}!\\.\root\cimv2")

wql = "SELECT ProcessId FROM Win32_Process WHERE Name = 'Wow.exe'"

For Each process In WMI. ExecQuery(wql)
    Shell.AppActivate process.ProcessId
Next