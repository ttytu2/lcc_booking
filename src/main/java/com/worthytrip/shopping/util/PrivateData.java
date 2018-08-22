package com.worthytrip.shopping.util;

import com.google.common.collect.Lists;
import com.worthytrip.shopping.dao.model.flightdata.FixedFlight;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PrivateData {

    public static ConcurrentHashMap<FixedFlight, Integer> NEED_GRAB_CACHE = new ConcurrentHashMap<>();

    public static List<String> TGAU_AIRPORTS = Lists.newArrayList("ADL", "BNE", "CNS", "CBR", "CFS", "DRW", "OOL", "HBA", "MEL", "PER", "SYD", "PPP", "TSV");

    public static List<String> SPRING_AIRPORTS = Lists.newArrayList("PVG", "HKG", "CJU", "TPE", "PNH", "BKK", "HKT");

    public static List<String> CEBU_AIRPORTS = Lists.newArrayList("MEL", "SYD", "BWN", "REP", "PEK", "CAN", "PVG", "XMN", "HKG", "DPS", "CGK", "FUK", "NGO", "KIX", "NRT", "MFM", "BKI", "KUL", "BCD", "BSO", "TAG", "MPH", "BXU", "CGY", "CYP", "CGM", "CYZ", "CEB", "CRK", "USU", "CBO", "DVO", "DPL", "DGT", "GES", "ILO", "KLO", "LGP", "MNL", "MBT", "WNP", "OZC", "PAG", "PPS", "RXS", "SJI", "IAO", "SUG", "TBH", "TAC", "TDG", "TWT", "TUG", "VRC", "ZAM", "SIN", "PUS", "ICN", "TPE", "BKK", "DXB", "GUM", "HAN", "SGN", "MEL", "SYD", "BWN", "REP", "PEK", "CAN", "PVG", "XMN", "HKG", "DPS", "CGK", "FUK", "NGO", "KIX", "MFM", "BKI", "KUL", "BCD", "BSO", "TAG", "MPH", "BXU", "CGY", "CYP", "CYZ", "CEB", "CRK", "USU", "CBO", "DVO", "DPL", "GES", "ILO", "KLO", "LGP", "MNL", "MBT", "WNP", "OZC", "PAG", "PPS", "RXS", "SJI", "IAO", "SUG", "TBH", "TAC", "TDG", "TUG", "VRC", "ZAM", "SIN", "PUS", "ICN", "TPE", "BKK", "DXB", "HAN", "SGN");
}
