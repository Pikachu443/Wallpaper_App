#define MyAppName "Your Application Name"
#define MyAppVersion "1.0"
#define MyAppPublisher "Your Company Name"
#define MyAppURL "http://www.example.com/"
#define MyAppExeName "percy.exe"
#define MyAppIcon "icon.ico"
#define MyAppFileDescription "Your Application Description"
#define MyAppFileVersion "1.0.0"
#define MyAppCompanyName "Your Company Name"
#define MyAppOriginalFilename "percy.exe"

[Setup]
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputBaseFilename=Setup

[Files]
Source: "{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\{#MyAppIcon}"

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "Launch {#MyAppName}"; Flags: nowait postinstall skipifsilent

[VersionInfo]
FileDescription={#MyAppFileDescription}
FileVersion={#MyAppFileVersion}
CompanyName={#MyAppCompanyName}
OriginalFilename={#MyAppOriginalFilename}
