public static class Main{
    int n = 100_000;
    int a[] = new int[n];
    for (int i = 0; i < n; i++) { 
        a[i] = n - i;
    }
    long t1 = System.currentTimeMillis();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (a[j] > a[j + 1]) {
                int aux = a[j];
                a[j] = a[j + 1];
                a[j + 1] = aux; //there is no swap method (!)
            }
        }
    }
    long t2 = System.currentTimeMillis();
    System.out.println(t2 - t1); 
}