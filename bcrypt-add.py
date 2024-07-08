import sys
import json
import bcrypt

JSON_FILE = "bcrypt.json"

def main():
    if len(sys.argv) < 4:
        print("Usage: python bcrypt-add.py <username> <password> <rounds>")
        sys.exit(1)

    username = sys.argv[1]
    password = sys.argv[2]
    rounds = int(sys.argv[3])

    # Generate salt for bcrypt
    salt = bcrypt.gensalt(rounds)

    # Hash password using bcrypt
    hashedPassword = bcrypt.hashpw(password.encode('utf-8'), salt)

    newUser = {
        "username": username,
        "password": hashedPassword.decode('utf-8'),
        "salt": salt.decode('utf-8')
    }

    try:
        with open(JSON_FILE, 'r') as file:
            usersArray = json.load(file)
    except FileNotFoundError:
        usersArray = []

    # Add new user to JSON array
    usersArray.append(newUser)

    try:
        with open(JSON_FILE, 'w') as file:
            json.dump(usersArray, file, indent=4)

        print(f"User '{username}' added to {JSON_FILE}")

    except Exception as e:
        print(f"An error occurred: {e}")

if __name__ == "__main__":
    main()
