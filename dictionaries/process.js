const fs = require('fs');
const readline = require('readline');
const deaccenter = require('./deaccenter')
const inquirer = require('inquirer');

const select_dictionary_question = {
  type: 'checkbox',
  message: 'Select dictionary',
  name: 'dictionaries',
  choices: [
    {
      name: 'Greek',
      value: {
        "filepath" : "./el_dictionary",
        "deaccent" : deaccenter.el
      }
    },
    {
      name: 'English',
      value: {
        "filepath" : "./en_dictionary",
        "deaccent" : deaccenter.en
      }
    },
    {
      name: 'German',
      value: {
        "filepath" : "./de_dictionary",
        "deaccent" : deaccenter.de
      }
    },
    {
      name: 'French',
      value: {
        "filepath" : "./fr_dictionary",
        "deaccent" : deaccenter.fr
      }
    },
  ],
  validate: function(answer) {
    if (answer.length < 1) {
      return 'You must choose at least one dictionary.';
    }
    return true;
  }
}

const output_question = {
  type: 'list',
  name: 'export_type',
  message: 'Which format do you want to export?',
  choices: [
    {
      name: 'json'
    },
    {
      name: 'csv'
    }
  ]
}

inquirer
  .prompt([select_dictionary_question, output_question])
  .then(answers => {
    const exportType = answers.export_type
    answers.dictionaries.forEach(dictionary => {
        proccessDictionary(dictionary.filepath, dictionary.deaccent, exportType)
    });
  })
  .catch(function(error) {
    console.log(error);
  });

function proccessDictionary(filePath, deaccent, exportType) {
    const dictionaryName = filePath.split('/').pop()
    console.log("Processing " + dictionaryName + " ...")

    const readFile = readline.createInterface({
      input: fs.createReadStream(filePath),
      output: fs.createWriteStream(dictionaryName + "." + exportType),
      terminal: false
    });

    const anagrams = {}
      
    readFile
      .on('line', function(word) {
        const word_transformed = sortAlphabetically(deaccent(word))
        // console.log(word + " => " + word_transformed)
        var anagram = anagrams[word_transformed] || ""
        if (anagram == "") {
          anagram = word
        } else {
          anagram += "|" + word
        }
        
        anagrams[word_transformed] = anagram
      })
      .on('close', function() {
        console.log("Exporting to file...")
        if(exportType == "json") {
          this.output.write(JSON.stringify(anagrams));
        } else {
          for (const [key, value] of Object.entries(anagrams)) {
            this.output.write(key + ";" + value + "\n");
          }
        }
        console.log(`Created "${this.output.path}"`);
      });
}

function sortAlphabetically(word) {
  var wordSplitted = word.split("")
  return wordSplitted.sort().join("")
}
