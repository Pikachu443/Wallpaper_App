import pefile
import sys

def update_metadata(exe_path):
    pe = pefile.PE(exe_path)

    pe.VS_FIXEDFILEINFO[0].FileVersionMS = 0x00010000
    pe.VS_FIXEDFILEINFO[0].FileVersionLS = 0x00000000
    pe.VS_FIXEDFILEINFO[0].ProductVersionMS = 0x00010000
    pe.VS_FIXEDFILEINFO[0].ProductVersionLS = 0x00000000

    # Updating StringFileInfo
    for fileinfo in pe.FileInfo[0]:
        if fileinfo.Key == b'StringFileInfo':
            for st in fileinfo.StringTable:
                st.entries[b'CompanyName'] = b'Your Company Name'
                st.entries[b'FileDescription'] = b'Your Application Description'
                st.entries[b'FileVersion'] = b'1.0.0.0'
                st.entries[b'InternalName'] = b'YourAppName'
                st.entries[b'LegalCopyright'] = b'Copyright (C) Your Company Name'
                st.entries[b'OriginalFilename'] = b'YourAppName.exe'
                st.entries[b'ProductName'] = b'Your Product Name'
                st.entries[b'ProductVersion'] = b'1.0.0.0'

    pe.write(exe_path)
    pe.close()

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: update_metadata.py <exe_path>")
        sys.exit(1)

    exe_path = sys.argv[1]
    update_metadata(exe_path)
    print(f"Metadata updated for {exe_path}")
