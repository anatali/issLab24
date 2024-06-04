class SRLatch {
  constructor() {
    this.s = false; // Input S
    this.r = false; // Input R
    this.q = false; // Output Q
    this.qBar = true; // Output QBar (complement of Q)
  }

  // Set the input S
  setInputS(value) {
    this.s = value;
    this.computeOutputs();
  }

  // Set the input R
  setInputR(value) {
    this.r = value;
    this.computeOutputs();
  }

  // Compute the outputs Q and QBar based on current inputs S and R
  computeOutputs() {
    if (this.s && this.r) {
      // Invalid state: S and R should not be true at the same time
      console.error("Invalid state: Both S and R are true");
      return;
    } else if (this.s) {
      this.q = true;
      this.qBar = false;
    } else if (this.r) {
      this.q = false;
      this.qBar = true;
    }
    // If both S and R are false, the outputs retain their previous states
  }

  // Get the current state Q
  getStateQ() {
    return this.q;
  }

  // Get the current state QBar
  getStateQBar() {
    return this.qBar;
  }
}

// Example usage:
const latch = new SRLatch();

// Set inputs
latch.setInputS(true);
latch.setInputR(false);

// Get outputs
console.log("Q:", latch.getStateQ()); // Output: true
console.log("QBar:", latch.getStateQBar()); // Output: false

// Change inputs
latch.setInputS(false);
latch.setInputR(true);

// Get outputs again
console.log("Q:", latch.getStateQ()); // Output: false
console.log("QBar:", latch.getStateQBar()); // Output: true

// Test invalid state
latch.setInputS(true);
latch.setInputR(true); // Invalid state: Both S and R are true
