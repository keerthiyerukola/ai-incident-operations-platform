Get-Content .env | ForEach-Object {
    if ($_ -match '^\s*([^#=\s]+)\s*=(.*)$') {
        [Environment]::SetEnvironmentVariable($Matches[1].Trim(), $Matches[2].Trim(), 'Process')
    }
}
mvn clean install


mvn spring-boot:run

$body = @{
    email = "test@example.com"
    password = "pass123"\
} | ConvertTo-Json

$response = Invoke-RestMethod `
    -Uri "http://localhost:8080/api/auth/login" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body

$token = $response.token


curl.exe -i `                                              
>>     "http://localhost:8080/api/users/admin-test" `
>>     -H "Authorization: Bearer $token"


$json = '{"name":"Platform Admin","email":"admin2@test.com","password":"AdminPass123!","role":"VIEWER"}'
>> 
>> # Confirm this displays valid JSON:
>> $json
>> 
>> Invoke-RestMethod `
>>   -Uri "http://localhost:8080/api/auth/register" `
>>   -Method Post `
>>   -ContentType "application/json" `
>>   -Body $json