def directoryName = args[1]
def fileSubStr = args[3]
def filePattern = ~/${fileSubStr}/
def dir = new File(directoryName);
def find = args[5]
def replacement = args[7]
def viewDirectory = args[9]
def files = [];

//Checks if path is directory
if(!dir.isDirectory()){
   println "The provided directory name ${directoryName} is NOT a directory."
   System.exit(-2)
}

//Searching of files to be modified based on a given pattern
println "Searching for files including ${fileSubStr} in directory ${directoryName}..."
def findFilenameClosure =
{
   if (filePattern.matcher(it.name).find())
   {
      it.write(it.text.replaceAll(find,replacement))
      files.add(it)
   }
}
dir.eachFileRecurse(findFilenameClosure)

// View Modified Files in a directory
if(viewDirectory.toBoolean()){
    println "Modified Files"
    println files
}
