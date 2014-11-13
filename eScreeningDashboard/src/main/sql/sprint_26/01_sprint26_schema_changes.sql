alter table battery add column welcome_message TEXT NULL;

alter table battery add column complete_message TEXT NULL;

update battery set welcome_message = 
' <h1>Welcome to VA San Diego Healthcare System!</h1>
  <p>We thank you for your service to our country and look forward to assisting in your enrollment for VA healthcare and transition from active duty to Veteran status. </p>
  <p>The following eScreening Questionnaire will help your VA healthcare team to assess your needs and provide the best health and wellness options available to you. Please take your time and answer all questions as completely as possible. If you have any questions, please ask for assistance.</p>
' where battery_id = 5;