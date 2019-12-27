import java.util.*;
/**
 * Class creates a random sentence by list containing non-terminals and terminals
 * it organizes it into a map where nonterminals are keys and terminals are values
 * Uses Recursion to create the sentence.
 *
 * @author Blezyl Santos
 * @version 2.12 (Feb 12 2019)
 */
public class GrammarSolver{
   private Map<String, String[]> BNF;
   private Set<String> nonTerminal;
   
   /**
    * Constructor that read through a given list and organizes into a map of non-terminals 
    * and terminals. Throws an exception if the list is empty or null. Initializes and declares the
    * non-terminal as nonTerminal. 
    *
    * @param rule a list of strings that contains the non-terminals and terminals
    * @throws IllegalArgumentException if the given list is empty or null 
    * @throws IllegalArgumentException if the non-terminal already exists
    */
   public GrammarSolver(List<String> rule){
      if (rule.isEmpty()||rule == null) throw new IllegalArgumentException();
      nonTerminal = new TreeSet<String>();
      BNF = new TreeMap<String, String[]>();
      for (int i = 0; i < rule.size(); i++){
         String[] line = rule.get(i).split("::=");
         String[] term = {line[1]};
         if (line[1].contains("|")){
            term = line[1].split("[|]");
         }
         for (int j=0; j < term.length; j++){
            term[j] = Trim(term[j]);
         } 
         if (BNF.containsKey(line[0])){
            throw new IllegalArgumentException();
         } else {
            line[0] = Trim(line[0]);
            BNF.put(line[0], term);
         }
      }
      for (String nonterm: BNF.keySet()){
         nonTerminal.add(nonterm);
      }
   }
   /**
    * Checks if the given symbol is a non-terminal
    *
    * @param symbol the word or non-terminal to check
    * @return true if the symbol is a non-terminal
    * @throws IllegalArgumentException() if the symbol is empty 
    */
   public boolean contains(String symbol){
      if(symbol.isEmpty()) throw new IllegalArgumentException();
      boolean answer = false; //default
      if (nonTerminal.contains(symbol)) answer = true;
      return answer;
   }
   /**
    * returns the all the non-terminals
    * @return nonTerminal set of string of nonTerminal
    */
   public Set<String> getSymbols(){
      return nonTerminal;
   }
   /**
    * Generates the random sentence by using recursion. It accepts a symbol and 
    * only returns a word if it is not a non-terminal, if it is non-terminal it picks a rule and
    * generate the rule. Does this until a sentence is made.
    *
    * @param symbol a non-terminal or terminal that decides what to return
    * @return symbol if the symbol is a terminal
    * @return answer if the symbol is a non-terminal 
    */
   public String generate(String symbol){
      if(!(nonTerminal.contains(symbol))){
         return symbol; //base
      } else {
         Random rand = new Random();
         int num = rand.nextInt(BNF.get(symbol).length);
         String answer = "", temp = "";
         String[] value = BNF.get(symbol);
         String[] rules = value[num].split("[ \t]");
         for(int i= 0; i < rules.length; i++){
            rules[i] = Trim(rules[i]);
            temp += generate(rules[i]);  // recursion
         }  
         // makes sure there is only one space between words
         String[] ans = temp.split("[ \t]");
         for (int i=0; i<ans.length; i++){
            ans[i] = Trim(ans[i]);
            answer += ans[i] + " ";
         }
         return answer;
      }
   }
   /**
    * Trims the word from an array. Made the method because it wouldn't trim directly from an array
    * @param word the string from an array
    * @return String the word that is trimmed
    */
   public String Trim(String word){
      return word.trim();
   }
}  