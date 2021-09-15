Feature: Validate HomeLoanRepayment Calculator
@HomeLoan
  Scenario Outline: Verify HomeLoan Repayment Calculator attribute Validation
  
     Given User Launched "https://www.commbank.com.au" 
     And  Navigate to Home Loan Repayment Calculator 
     When User Entered   <BorrowAmount>,<Duration>,<RepaymentType>,<InterestRate> and clicked on Calculate
     Then  Verify Total Loan Repayment and Total Interest charged
   
     
  Examples:
  
	    | BorrowAmount | Duration | RepaymentType          | InterestRate                                     | 
      | 10000        | 10       | Principal and interest | 2.69% p.a. Extra Home Loan 30% deposit           | 
      | 10000        | 5        | Interest only 1 year   | 5.04% p.a. Standard Variable Rate Home Loan      | 
      | 10000        | 20       | Principal and interest | 2.69% p.a. Extra Home Loan 30% deposit           | 
      | 5000         | 10       | Interest only 2 years  | 4.69% p.a. Extra Investment Home Loan 10% deposit | 
      | 5000         | 10       | Principal and interest  | 0.99% p.a. CommBank Green Loan                   | 
      | 4999         | 10       | Principal and interest | 0.99% p.a. CommBank Green Investment Loan        | 
      | 20000        | 5        | Interest only 3 year   | 4.04% p.a. 5 Year Fixed Rate Home Loan           | 
      | 20000        | 5        | Interest only 3 years  | 4.04% p.a. 5 Year Fixd Rate Home Loan            | 
  
