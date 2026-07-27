class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int p : getPrimeFactors(nums[i])) {
                map.computeIfAbsent(p, k -> new ArrayList<>()).add(i);
            }
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[n];

        q.offer(0);
        vis[0] = true;

        int steps = 0;

        while (!q.isEmpty()) {
            int size = q.size();

            while (size-- > 0) {
                int i = q.poll();

                if (i == n - 1) return steps;

                if (i - 1 >= 0 && !vis[i - 1]) {
                    vis[i - 1] = true;
                    q.offer(i - 1);
                }

                if (i + 1 < n && !vis[i + 1]) {
                    vis[i + 1] = true;
                    q.offer(i + 1);
                }

                // Prime teleportation
                if (isPrime(nums[i])) {
                    int p = nums[i];

                    List<Integer> list = map.get(p);
                    if (list != null) {
                        for (int j : list) {
                            if (!vis[j]) {
                                vis[j] = true;
                                q.offer(j);
                            }
                        }
                        // Use this prime only once
                        map.remove(p);
                    }
                }
            }

            steps++;
        }

        return -1;
    }

    private boolean isPrime(int x) {
        if (x < 2) return false;
        if (x % 2 == 0) return x == 2;

        for (int i = 3; i * i <= x; i += 2) {
            if (x % i == 0) return false;
        }
        return true;
    }

    private List<Integer> getPrimeFactors(int x) {
        List<Integer> res = new ArrayList<>();

        for (int p = 2; p * p <= x; p++) {
            if (x % p == 0) {
                res.add(p);
                while (x % p == 0) x /= p;
            }
        }

        if (x > 1) res.add(x);

        return res;
    }
}