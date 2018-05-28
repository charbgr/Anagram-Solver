
const dict_deaccents = {
  el : el_deaccent,
  en : en_deaccent,
  de : de_deaccent,
  fr : fr_deaccent
}

if (require.main === module) {
  var args = process.argv.slice(2)
  if(args.length != 2) {
    console.log("You must run with: $ deaccenter [dict_key] [word]")
    console.log("e.g: $ deaccenter el παοκ")
    process.exit(1)
  }
  console.log(dict_deaccents[args[0]](args[1]));
} else {
  module.exports = dict_deaccents;
}

function el_deaccent(word) {
  return word.toLowerCase()
    .replace('ά', 'α')
    .replace('έ', 'ε')
    .replace('ή', 'η')
    .replace('ί', 'ι')
    .replace('ϊ', 'ι')
    .replace('ΐ', 'ι')
    .replace('ό', 'ο')
    .replace('ύ', 'υ')
    .replace('ύ', 'υ')
    .replace('ΰ', 'υ')
    .replace('ώ', 'ω')
}

function en_deaccent(word) {
  return word.toLowerCase()
}

function de_deaccent(word) {
  return word.toLowerCase()
    .replace('ä', 'ae')
    .replace('ö', 'oe')
    .replace('ü', 'ue')
    .replace('ß', "ss")
}

function fr_deaccent(word) {
  return word.toLowerCase()
    .replace('à', 'a')
    .replace('â', 'a')
    .replace('ä', 'a')
    .replace('ā', 'a')
    .replace('ç', 'c')
    .replace('č', 'c')
    .replace('è', 'e')
    .replace('é', 'e')
    .replace('é', 'e')
    .replace('ê', 'e')
    .replace('ë', 'e')
    .replace('ē', 'e')
    .replace('î', 'i')
    .replace('ï', 'i')
    .replace('ī', 'i')
    .replace('ô', 'o')
    .replace('ō', 'o')
    .replace('œ', 'o')
    .replace('ù', 'u')
    .replace('û', 'u')
    .replace('ü', 'u')
    .replace('ū', 'u')
    .replace('ÿ', 'y')
    .replace('ź', "z")
}
