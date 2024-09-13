function Logic() {

    // INPUT
    var sampleInput = "2 5 +";
  
    // Declare variables
    var a, b, operator;
  
    // Split the sample input into an array
    var inputArray = sampleInput.split(" ");
  
    // Get the two numbers
    a = parseInt(inputArray[0]);
    b = parseInt(inputArray[1]);
  
    // Get the operator
    operator = inputArray[2];
  
    // WRITE YOUR LOGIC HERE
  
    switch (operator) {
      case "+":
        result = a + b;
        break;
      case "-":
        result = a - b;
        break;
      case "*":
        result = a * b;
        break;
      case "/":
        result = a / b;
        break;
      default:
        console.log("Invalid operator");
        return;
    }
  
    // OUTPUT
    console.log(result);
  }

  Logic();