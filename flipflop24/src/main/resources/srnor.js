class NORGate {
  constructor() {
    this.input1 = false;
    this.input2 = false;
    this.output = true; // NOR gate output starts as true (NOT (0 OR 0) = 1)
  }

  // Set the first input of the NOR gate
  setInput1(value) {
    this.input1 = value;
    this.computeOutput();
  }

  // Set the second input of the NOR gate
  setInput2(value) {
    this.input2 = value;
    this.computeOutput();
  }

  // Compute the output of the NOR gate
  computeOutput() {
    this.output = !(this.input1 || this.input2);
  }

  // Get the current output of the NOR gate
  getOutput() {
    return this.output;
  }
}

class SRLatch {
  constructor() {
    this.nor1 = new NORGate();
    this.nor2 = new NORGate();
  }

  // Set the input S
  setInputS(value) {
    this.nor1.setInput1(value);
    this.nor2.setInput2(this.nor1.getOutput());
    this.nor1.setInput2(this.nor2.getOutput());
  }

  // Set the input R
  setInputR(value) {
    this.nor2.setInput1(value);
    this.nor1.setInput2(this.nor2.getOutput());
    this.nor2.setInput2(this.nor1.getOutput());
  }

  // Get the current state Q
  getStateQ() {
    return this.nor1.getOutput();
  }

  // Get the current state QBar
  getStateQBar() {
    return this.nor2.getOutput();
  }
}

// Example usage:
const latch = new SRLatch();

// Set inputs
latch.setInputS(true);
latch.setInputR(false);

// Get outputs
console.log("Q:", latch.getStateQ()); // Output: false
console.log("QBar:", latch.getStateQBar()); // Output: true

// Change inputs
latch.setInputS(false);
latch.setInputR(true);

// Get outputs again
console.log("Q:", latch.getStateQ()); // Output: true
console.log("QBar:", latch.getStateQBar()); // Output: false

// Test both inputs low (latch should retain previous state)
latch.setInputS(false);
latch.setInputR(false);
console.log("Q:", latch.getStateQ()); // Output should be the same as previous state
console.log("QBar:", latch.getStateQBar()); // Output should be the same as previous state

// Test invalid state
latch.setInputS(true);
latch.setInputR(true); // Invalid state: Both S and R are true
console.log("Q (invalid):", latch.getStateQ());
console.log("QBar (invalid):", latch.getStateQBar());


 
