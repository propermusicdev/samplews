package data.core;

import data.ScanTest;

import java.util.List;

/**
 * Created by Lebel on 01/04/2014.
 */
public interface IScanTestRepository {
    ScanTest GetScanById(int transId);
    ScanTest AddScan(ScanTest test);
    List<ScanTest> GetTop20Scans();
}
