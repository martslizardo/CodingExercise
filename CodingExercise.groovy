import groovy.time.TimeCategory 
import groovy.time.TimeDuration

def directoryName = args[1]
def dir = new File(directoryName);
import groovy.io.FileType

def find = args[3]
def replacement = args[5]
def viewDirectory = args[7]
def files = [];
def stringPattern = ~/${find}/
def fileContents
def matchCounter = 0


def start = new Date()
println "Start Time :${start.format("yyyy-MM-dd HH.mm.ss.SSSSS Z")}"
//Checks if path is directory
if(!dir.isDirectory()){
   println "The provided directory name ${directoryName} is NOT a directory."
   System.exit(-2)
}

//Listing of files to be modified given if it satisfies the given string pattern.
println "Searching for files in directory ${directoryName}..."
def listFiles =
{
      it.eachLine { line ->
         if (stringPattern.matcher(line).matches()) {
            it.write(it.text.replaceAll(stringPattern,replacement))
            files.add(it)
            matchCounter++
         }
      }
}
dir.eachFileRecurse(listFiles)



//Error handling for 
if(matchCounter === 0){
   println "The provided pattern ${stringPattern} does not exist in any of the files."
}

// View Modified Files in a directory
if(viewDirectory.toBoolean() && matchCounter != 0){
    println "Modified Files: ${files}"
}

def end = new Date()
println "End Time: ${end.format("yyyy-MM-dd HH.mm.ss.SSSSS Z")}"


def td = end.getTime() - start.getTime()
println "Duration: ${td.toString()}ms"
